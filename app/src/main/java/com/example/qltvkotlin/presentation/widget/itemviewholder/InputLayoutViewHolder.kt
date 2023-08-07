package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemTextinputlayoutBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.model.Bindable
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.cast
import com.google.android.material.textfield.TextInputLayout


abstract class InputLayoutViewHolder(
    parent: ViewGroup,
    val binding: ItemTextinputlayoutBinding = parent.bindingOf(ItemTextinputlayoutBinding::inflate)
) : RecyclerView.ViewHolder(binding.root), Bindable<IInputLayoutField>,
    Signal.Closable by Signal.Bags(), HasCommandCallback {
    override var onCommand: (Command) -> Unit = {}
    var itemLayout: IInputLayoutField? = null


    override fun bind(item: IInputLayoutField) {
        this.itemLayout = item
        binding.edittext.hint
        binding.edittext.setHint(item.hint)
        binding.edittext.maxEms = item.maxEms
        binding.edittext.inputType = item.inputType
        binding.edittext.isSingleLine = item.singleLine
        binding.edittext.isEnabled = item.enabled
        binding.layoutText.addListenerField(item)

    }


    private fun TextInputLayout.addListenerField(it: IInputLayoutField) {
        if (it.hasListener) {
            checkAndShowError(it, this)
            itemLayout.cast<Signal>()?.bind { checkAndShowError(it, this) }
        }
    }

}



