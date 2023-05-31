package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.domain.observable.Signal

open class Chars(private var chars: String) : CharSequence, Updatable, HasIsValid, Validable, GetError, Signal by Signal.MultipleSubscription() {
    override val length = chars.length
    private lateinit var error: String
    override fun get(index: Int): Char {
        return chars[index]
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return chars.subSequence(startIndex, endIndex)
    }

    override fun update(value: Any?) {
        this.chars = value?.toString().orEmpty()
    }

    override val isValid= this.chars.isNotBlank()
    override fun getError() = error

    override fun validate(): Boolean {
        val mValue = this.isValid
        if(!mValue) error = "Không Để Trống"
        return mValue.also { emit() }
    }

    override fun toString(): String {
        return chars
    }
}

