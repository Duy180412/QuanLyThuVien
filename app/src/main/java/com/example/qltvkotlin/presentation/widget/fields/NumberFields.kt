package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.HasMaxUpdate


class NumberFields(
    fieldsID: FieldsId,
    private var textInput: String
) : InputLayoutField(fieldsID, textInput), HasMaxUpdate {

    private var max: Int? = null

    override fun update(value: Any?) {
        this.textInput = value?.toString().orEmpty()
        super.update(value)
    }

    override fun setMax(value: String) {
        this.max = value.toIntOrNull()
        emit()
    }

    override fun validate(): Boolean {
        if (this.textInput.toIntOrNull() == null && max == null) {
            errorValue = "Chưa Chọn Sách"
            return false
        }
        if (this.textInput.toIntOrNull() == null && max != null) {
            errorValue = "Not Empty"
            return false
        }
        if (this.textInput.toInt() < 1) {
            errorValue = "Min = 1"
            return false
        }
        if (this.textInput.toInt() > max!!) {
            errorValue = "Max = $max"
            return false
        }

        return true
    }


}


