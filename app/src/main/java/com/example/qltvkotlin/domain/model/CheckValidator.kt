package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.feature.presentation.extension.cast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun checkValidator(it:CharSequence, editText: TextInputLayout ){
    val value =  it.cast<HasIsValid>()?.isValid!!
    val error =  if(!value) it.cast<GetError>()?.getError() else ""
    editText.isErrorEnabled = value
    editText.error = error
}