package com.example.qltvkotlin.domain.model

import java.util.Date

interface Updatable {
    fun update(value: Any?)
}

interface Validable {
    fun validate(): Boolean
}
interface GetError {
    fun getMess():String
}
interface GetDate {
    fun getDate(): Date?
}



