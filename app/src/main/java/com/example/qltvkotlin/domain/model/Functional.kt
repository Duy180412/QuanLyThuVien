package com.example.qltvkotlin.domain.model


interface Updatable {
    fun update(value: Any?)
}

interface Validable {
    fun validate(): Boolean
}
interface HasError {
    fun getError():String
}
interface Bindable<T> {
    fun bind(item: T)
}



