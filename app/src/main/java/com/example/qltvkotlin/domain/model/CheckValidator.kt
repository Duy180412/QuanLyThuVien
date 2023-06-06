package com.example.qltvkotlin.domain.model

import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.qltvkotlin.domain.observable.IDestroyObsever
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.google.android.material.textfield.TextInputLayout


fun checkValidatorOwer(ownr: LifecycleOwner, it: CharSequence, mValue: (CharSequence) -> Unit) {
    val closeable = it.cast<Signal>()?.subscribe { mValue.invoke(it) }
    val checkOwnr = if (ownr is Fragment) ownr.viewLifecycleOwner else ownr
    checkOwnr.lifecycle.addObserver(object : IDestroyObsever {
        override fun onDestroyed() {
            closeable!!.close()
        }
    })
}
fun checkImageOwer(ownr: LifecycleOwner, it: IImage, mValue: (IImage) -> Unit) {
    val closeable = it.cast<Signal>()?.subscribe { mValue.invoke(it) }
    val checkOwnr = if (ownr is Fragment) ownr.viewLifecycleOwner else ownr
    checkOwnr.lifecycle.addObserver(object : IDestroyObsever {
        override fun onDestroyed() {
            closeable!!.close()
        }
    })
}


fun checkValidator(it: CharSequence, editText: TextInputLayout) {
    val value = it.cast<Validable>()?.validate()!!
    editText.isErrorEnabled = !value
    val error = if (!value) it.cast<GetError>()?.getError() else return
    editText.error = error
}