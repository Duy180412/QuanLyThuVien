package com.example.qltvkotlin.domain

interface test {
    val maSach: String get() = ""
}

fun chaythu() {
    object : test{
        override val maSach: String
            get() = "chay2"
    }
}