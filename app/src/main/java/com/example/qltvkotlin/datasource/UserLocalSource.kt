package com.example.qltvkotlin.datasource

import java.util.concurrent.atomic.AtomicBoolean

class UserLocalSource {
    private val checkLogin = AtomicBoolean(false);
    fun setValue() {
        checkLogin.set(true)
    }

    fun getValue(): Boolean {
        return checkLogin.get()
    }

    fun restValue() {
        checkLogin.set(false)
    }
}