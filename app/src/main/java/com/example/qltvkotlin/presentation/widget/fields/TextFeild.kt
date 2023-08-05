package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.model.ITextInputLayoutField

class TextFeild(
    stringID: StringId,
    textInput: String = ""
) : InputLayoutField(stringID, textInput),ITextInputLayoutField