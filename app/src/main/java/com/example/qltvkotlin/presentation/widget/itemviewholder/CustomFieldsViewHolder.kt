package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemTextinputlayoutselectBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.SelectSachMuon
import com.example.qltvkotlin.domain.model.Bindable
import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.IHasItemStart
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.widget.fields.SelectTextFeild
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class CustomFieldsViewHolder(
    parent: ViewGroup,
    private val binding: ItemTextinputlayoutselectBinding = parent.bindingOf(
        ItemTextinputlayoutselectBinding::inflate
    )
) : RecyclerView.ViewHolder(binding.root), HasCommandCallback, Bindable<IFieldsCustom>,
    Signal.Closable by Signal.Bags() {
    override var onCommand: (Command) -> Unit = {}
    private var itemLayout: IInputLayoutField? = null
    private val textWatcher = binding.number.edittext.addTextChangedListener {
        itemLayout.cast<Updatable>()?.update(it.toString())
    }


    override fun bind(item: IFieldsCustom) {
        val text = item.getSelectFeild()
        val number = item.getNumberFeild()
        itemLayout = number
        binding.text.edittext.bindPropertiesField(text)
        binding.text.layoutText.addListenerField(text)
        binding.text.layoutText.bindIconStart(text)
        binding.text.layoutText.setStartIconOnClickListener {
            onCommand(SelectSachMuon(item))
        }
        //
        binding.number.edittext.bindPropertiesField(number)
        binding.number.edittext.setTextWithouNotify(number)
        binding.number.layoutText.addListenerField(number)
    }

    private fun TextInputLayout.addListenerField(it: IInputLayoutField) {
        if (it.hasListener) {
            checkAndShowError(it, this)
            it.cast<Signal>()?.bind { checkAndShowError(it, this) }
        }

    }

    private fun TextInputEditText.bindPropertiesField(item: IInputLayoutField) {
        this.maxEms = item.maxEms
        this.setHint(item.hint)
        this.inputType = item.inputType
        this.isSingleLine = item.singleLine
        this.isEnabled = item.enabled
        item.cast<Signal>()?.bind {
            this.setText(item)
        }
    }

    private fun TextInputLayout.bindIconStart(text: SelectTextFeild) {
        val mItem = text.cast<IHasItemStart>() ?: return
        if (mItem.hasItem) {
            this.setStartIconDrawable(mItem.resId)
        }

    }

    private fun TextInputEditText.setTextWithouNotify(item: IInputLayoutField) {
        removeTextChangedListener(textWatcher)
        setText(item)
        addTextChangedListener(textWatcher)
    }


}






