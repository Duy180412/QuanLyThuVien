package com.example.qltvkotlin.domain.model
interface HasIsValid {
    val isValid: Boolean
}
interface HasChange{
    fun hasChange():Boolean
}
interface IChar :CharSequence{
    override val length: Int get() = toString().length
    override fun get(index: Int): Char = toString()[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return toString().subSequence(startIndex, endIndex)
    }

    override fun toString(): String
}


