package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.presentation.extension.cast
import com.google.android.material.textfield.TextInputEditText

abstract class EnterTextLayoutViewHolder(parent: ViewGroup
): InputLayoutViewHolder(parent) {

    private val textWatcher = binding.edittext.addTextChangedListener {
        itemLayout.cast<Updatable>()?.update(it.toString())
    }

    override fun bind(item: IInputLayoutField) {
        super.bind(item)
        binding.edittext.setTextWithouNotify(item)

    }
    private fun TextInputEditText.setTextWithouNotify(item: IInputLayoutField) {
        removeTextChangedListener(textWatcher)
        setText(item)
        addTextChangedListener(textWatcher)
    }
}