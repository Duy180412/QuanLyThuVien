package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.enumeration.StringIds
import com.example.qltvkotlin.domain.model.HasFieldsIds
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IFieldsCustom

class CustomManyFeilds(
    private val stringIds: StringIds,
    textInput: String = "",
    numberInput: String = ""
) : ITextAndNumberFeilds, HasFieldsIds, IFieldsCustom {
    private val selectTextFeild: SelectTextFeild = SelectTextFeild(StringId.SachThue, textInput)
    private val numberFields: NumberFields = NumberFields(StringId.SoLuongThueCuaSach, numberInput)
    override fun getFieldsIds(): StringIds =  stringIds
    override fun getSelectFeild(): SelectTextFeild = selectTextFeild
    override fun getNumberFeild(): NumberFields = numberFields
}

interface IHasText {
    fun getSelectFeild(): SelectTextFeild
}

interface IHasNumber {
    fun getNumberFeild(): NumberFields
}

interface ITextAndNumberFeilds : IComponent

