package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.IHint
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.observable.signal
import com.example.qltvkotlin.presentation.helper.AppResources

abstract class InputLayoutField(
    private val fieldsID: FieldsId,
    private var textInput: String = ""
) : IInputLayoutField,
    Signal by signal() {
    private val resources: AppResources = AppResources.shared
    var errorValue: String = ""
    override var key: Any = ""
    override var iHint:IHint = resources[fieldsID].iHint
    override var maxEms = resources[fieldsID].maxEms
    override var enabled = resources[fieldsID].enabled
    override var inputType = resources[fieldsID].inputType
    override var singleLine = resources[fieldsID].singleLine
    override var hasListener = resources[fieldsID].hasListener
    override val backUp: String = textInput

    override val isValid: Boolean
        get() = this.textInput.isNotBlank()

    override fun hasChange(): Boolean {
        return backUp != textInput
    }

    override fun update(value: Any?) {
        this.textInput = value?.toString().orEmpty()
        emit()
    }

    override fun validate(): Boolean {
        val mValue = this.isValid
        if (!mValue) errorValue = "Không Để Trống"
        return mValue
    }
    override fun getError() = errorValue
    override fun getFieldsID(): FieldsId = fieldsID
    override fun getValue(): String = textInput
    override fun toString() = textInput
}

