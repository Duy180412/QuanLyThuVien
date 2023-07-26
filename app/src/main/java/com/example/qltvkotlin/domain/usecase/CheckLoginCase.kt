package com.example.qltvkotlin.domain.usecase

import com.example.qltvkotlin.data.datasource.SharedPreferencesExt

class CheckLoginCase {
    val shared = SharedPreferencesExt.instance
    operator fun invoke(): Boolean {
        return shared.isLogin()
    }
}