package com.example.qltvkotlin.feature.presentation.router

import com.example.qltvkotlin.feature.login.LoginActivity
import com.example.qltvkotlin.feature.main.MainActivity


object Routes : Routing {
    class Main : ActivityRouting{
        override val clazz = MainActivity::class
    }
    class Login : ActivityRouting{
        override val clazz = LoginActivity::class
    }
}