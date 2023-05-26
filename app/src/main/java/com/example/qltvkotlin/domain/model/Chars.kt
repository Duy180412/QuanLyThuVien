package com.example.qltvkotlin.domain.model

open class Chars(var chars: CharSequence):CharSequence {
    override val length = chars.length

    override fun get(index: Int): Char {
        return chars[index]
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
     return  chars.subSequence(startIndex,endIndex)
    }

    override fun toString(): String {
        return chars.toString()
    }
}