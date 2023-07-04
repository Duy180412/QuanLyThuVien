package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.domain.observable.Signal

open class Chars(private var chars: String) : CharSequence, Updatable, HasIsValid, Validable,
    GetError, Signal by Signal.MultipleSubscription() {

    var error: String = ""
    override val length: Int
        get() = chars.length

    override fun get(index: Int): Char {
        return chars[index]
    }

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
        return chars.subSequence(startIndex, endIndex)
    }

    override fun update(value: Any?) {
        this.chars = value?.toString().orEmpty()
        emit()
    }

    override val isValid: Boolean
        get() = this.chars.isNotBlank()


    override fun getMess() = error

    @Suppress("UNUSED_EXPRESSION")
    override fun validate(): Boolean {
        val mValue = this.isValid
        if (!mValue) error = "Không Để Trống" else ""
        return mValue
    }

    override fun toString(): String {
        return chars
    }
}

