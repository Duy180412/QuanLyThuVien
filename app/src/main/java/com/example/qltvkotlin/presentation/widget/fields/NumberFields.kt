package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.model.HasMaxUpdate


class NumberFields(
    stringID: StringId,
    private var textInput: String
) : InputLayoutField(stringID, textInput), HasMaxUpdate {
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
        if (this.textInput.toIntOrNull() == null) {
            errorValue = "Chưa Chọn Sách"
            return false
        }
        if (max == null) {
            errorValue = "Không Tìm Thấy Số Lượng Tối Đa"
            return false
        }
        if (this.textInput.toInt() > max!!) {
            errorValue = "Max = $max"
            return false
        }

        return true
    }


}


