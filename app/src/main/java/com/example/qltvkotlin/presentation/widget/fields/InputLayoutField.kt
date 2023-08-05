package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.observable.signal
import com.example.qltvkotlin.presentation.helper.AppResources

abstract class InputLayoutField(
    private val stringID: StringId,
    private var textInput: String = ""
) : IInputLayoutField,
    Signal by signal() {
    private val resources: AppResources = AppResources.shared
    var errorValue: String = ""
    override var key: Any = ""
    override var hint = resources[stringID].hint
    override var maxEms = resources[stringID].maxEms
    override var enabled = resources[stringID].enabled
    override var inputType = resources[stringID].inputType
    override var singleLine = resources[stringID].singleLine
    override var hasListener = resources[stringID].hasListener
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
    override fun getFieldsID(): StringId = stringID
    override fun getValue(): String = textInput
    override fun toString() = textInput
}

