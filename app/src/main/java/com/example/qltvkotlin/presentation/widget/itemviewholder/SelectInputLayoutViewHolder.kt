package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import com.example.qltvkotlin.domain.model.IHasItemStart
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.cast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

abstract class SelectInputLayoutViewHolder(parent: ViewGroup) : InputLayoutViewHolder(parent) {

    override fun bind(item: IInputLayoutField) {
        super.bind(item)
        binding.layoutText.addIconStart(item)
        binding.edittext.setTextWhenNotify(item)
    }

    private fun TextInputLayout.addIconStart(item: IInputLayoutField) {
        val mItem = item.cast<IHasItemStart>() ?: return
        if (mItem.hasItem) {
            this.setStartIconDrawable(mItem.resId)
        }
    }
    private fun TextInputEditText.setTextWhenNotify(item: IInputLayoutField) {
        item.cast<Signal>()?.bind {
            this.setText(item)
        }
    }

}

