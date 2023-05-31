package com.example.qltvkotlin.domain.repo

import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.domain.model.Chars
import com.example.qltvkotlin.domain.model.IAccount

class FetchAccountRepo {
    val result = MutableLiveData<IAccount>()
    operator fun invoke() {
        result.postValue(createAcoutnt("taikhoan", "matkhau"))
    }

    private fun createAcoutnt(id: String, pass: String): IAccount {
        return object : IAccount {
            override val id = Chars(id)
            override val password = Chars(pass)
        }
    }
}