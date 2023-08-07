package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.ITextAndNumberFeilds

class CustomManyFeilds(
    textInput: String = "",
    numberInput: String = ""
) : ITextAndNumberFeilds, IFieldsCustom {
    private val selectTextFeild: SelectTextFeild = SelectTextFeild(StringId.SachThue, textInput)
    private val numberFields: NumberFields = NumberFields(StringId.SoLuongThueCuaSach, numberInput)
    override fun getSelectFeild(): SelectTextFeild = selectTextFeild
    override fun getNumberFeild(): NumberFields = numberFields
}



