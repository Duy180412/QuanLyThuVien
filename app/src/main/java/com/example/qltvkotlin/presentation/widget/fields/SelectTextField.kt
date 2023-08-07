package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.IHasItemStart
import com.example.qltvkotlin.domain.model.ISelectTextField

class SelectTextField(
    fieldsId: FieldsId,
    private var textInput:String = ""
) : InputLayoutField(fieldsId,textInput), IHasItemStart,ISelectTextField {
    override var key: Any = ""
    override val hasItem = true
    override var resId = R.drawable.ic_add


    override fun update(value: Any?) {
        this.textInput = value?.toString().orEmpty()
        super.update(value)
    }
}