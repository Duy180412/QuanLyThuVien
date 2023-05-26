package com.example.qltvkotlin.domain.repo

import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.model.Chars
import com.example.qltvkotlin.domain.model.HasIsValid
import com.example.qltvkotlin.domain.model.IAccount
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.domain.model.Validable

class FetchAccountRepo {
    val result = MutableLiveData<IAccount>()
    operator fun invoke() {
        result.postValue(createAcoutnt("taikhoan", "matkhau"))
    }

    private fun createAcoutnt(id: String, pass: String): IAccount {
        return object : IAccount {
            override val id = createField(id)
            override val password = createField(pass)

        }
    }

    private fun createField(id: String): CharSequence {
        return object :
            Chars(id),
            Updatable,
            HasIsValid,
            Validable,
            Signal by Signal.MultipleSubscription() {

            override fun update(value: Any?) {
                this.chars = value?.toString().orEmpty()
            }

            override val isValid = this.chars.isNotBlank()
            override fun validate(): Boolean = this.isValid.also { emit() }
        }
    }


}