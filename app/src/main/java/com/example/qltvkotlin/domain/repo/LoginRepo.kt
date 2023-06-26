package com.example.qltvkotlin.domain.repo

import com.example.qltvkotlin.datasource.SharedPreferencesExt
import com.example.qltvkotlin.domain.model.IAccount
import com.example.qltvkotlin.domain.model.Validable
import com.example.qltvkotlin.feature.presentation.extension.cast

class LoginRepo {
    private val shared = SharedPreferencesExt.instance

    operator fun invoke(value: IAccount): Boolean {
        val isValid = arrayOf(value.id, value.password).all {
            it.cast<Validable>()?.validate() ?: true
        }
        if (!isValid) return false
        val boolean = shared.checkLogin(value)
        return true
    }
}


