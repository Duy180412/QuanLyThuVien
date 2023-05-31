package com.example.qltvkotlin.domain.model

interface Updatable {
    fun update(value: Any?)
}

interface Validable {
    fun validate(): Boolean
}
interface GetError {
    fun getError():String
}


