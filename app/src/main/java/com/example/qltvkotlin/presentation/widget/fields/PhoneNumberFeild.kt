package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.IPhoneNumberField

class PhoneNumberFeild(
    fieldsID: FieldsId,
    private var textInput: String = ""
) : InputLayoutField(fieldsID, textInput),IPhoneNumberField {


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