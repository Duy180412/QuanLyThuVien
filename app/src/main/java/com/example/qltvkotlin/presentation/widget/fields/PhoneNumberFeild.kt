package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.model.IPhoneNumberField
import com.example.qltvkotlin.domain.model.ITextInputLayoutField

class PhoneNumberFeild(
    stringID: StringId,
    private var textInput: String = ""
) : InputLayoutField(stringID, textInput),IPhoneNumberField {


    private fun checkConditionPhone(): Boolean {
        val regex = Regex("^0\\d{10,}$")
        return regex.matches(textInput)
    }

    override fun update(value: Any?) {
        this.textInput = value?.toString().orEmpty()
        super.update(value)
    }

    override fun validate(): Boolean {
        val mValue = super.validate()
        if (checkConditionPhone()) {
            errorValue = "Bắt đầu từ 0 và 11 chữ số"
            return false
        }
        return mValue
    }
}