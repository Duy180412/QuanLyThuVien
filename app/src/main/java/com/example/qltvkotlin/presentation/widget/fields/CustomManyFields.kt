package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.ITextAndNumberFields

class CustomManyFields(
    textInput: String = "",
    numberInput: String = ""
) : ITextAndNumberFields, IFieldsCustom {
    private val selectTextField: SelectTextField = SelectTextField(FieldsId.SachThue, textInput)
    private val numberFields: NumberFields = NumberFields(FieldsId.SoLuongThueCuaSach, numberInput)
    override fun getSelectField(): SelectTextField = selectTextField
    override fun getNumberField(): NumberFields = numberFields
}



