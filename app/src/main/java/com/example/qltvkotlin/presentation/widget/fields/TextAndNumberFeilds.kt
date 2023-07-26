package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.model.IComponent

class TextAndNumberFeilds(
    val stringId: StringId,
    var textInput: String = "",
    var numberInput: String= "",
    private val selectTextFeild: SelectTextFeild = SelectTextFeild(stringId, textInput),
    private val numberFieldsId: NumberFieldsId = NumberFieldsId(stringId, numberInput)
) : ITextAndNumberFeilds {


}

interface ITextAndNumberFeilds : IComponent

