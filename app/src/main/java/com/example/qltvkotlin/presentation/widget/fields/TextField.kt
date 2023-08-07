package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.ITextInputLayoutField

class TextField(
    fieldId: FieldsId,
    textInput: String = ""
) : InputLayoutField(fieldId, textInput),ITextInputLayoutField