package com.example.qltvkotlin.domain.model

class PhoneNumberChar(private var chars: String) : Chars(chars) {


    private fun checkConditionPhone(): Boolean {
        val regex = Regex("^0\\d{9,}$")
        return regex.matches(chars)
    }

    override fun update(value: Any?) {
        this.chars = value?.toString().orEmpty()
        super.update(value)

    }

    override fun validate(): Boolean {
        if (!super.validate()) return false
        if (!checkConditionPhone()) {
            error = "Sđt bắt đầu từ 0 và tối thiếu 11 số"
            return false
        }
        return true
    }

}