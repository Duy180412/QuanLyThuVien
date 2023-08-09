package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemTextinputlayoutselectBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.RemoveField
import com.example.qltvkotlin.domain.enumeration.SelectSachMuon
import com.example.qltvkotlin.domain.model.Bindable
import com.example.qltvkotlin.domain.model.ICharsHint
import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.IHasItemStart
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.model.IIntHint
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.widget.fields.NumberFields
import com.example.qltvkotlin.presentation.widget.fields.SelectTextField
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
        val text = item.getSelectField()
        val number = item.getNumberField()
        itemLayout = number
        binding.text.edittext.bindPropertiesField(text)
        binding.text.layoutText.addListenerField(text)
        binding.text.edittext.bindTextWithoutChange(text)
        binding.text.layoutText.bindIconStart(text)
        binding.text.layoutText.setStartIconOnClickListener {
            onCommand(SelectSachMuon(item))
        }
        //
        binding.number.edittext.bindPropertiesField(number)
        binding.number.edittext.setTextWithoutNotify(number)
        binding.number.layoutText.addListenerField(number)
        binding.number.edittext.bindWithoutNotify(number)
        //
        binding.removefield.onClick{
            onCommand(RemoveField(item))
        }
    }

    private fun TextInputLayout.addListenerField(it: IInputLayoutField) {
        if (it.hasListener) {
            checkAndShowError(it, this)
            it.cast<Signal>()?.bind { checkAndShowError(it, this) }
        }

    }

    private fun TextInputEditText.bindPropertiesField(item: IInputLayoutField) {
        this.maxEms = item.maxEms
        when (val valueHint = item.iHint){
            is IIntHint -> this.setHint(valueHint.hint)
            is ICharsHint -> this.hint = valueHint.hint
        }
        this.inputType = item.inputType
        this.isSingleLine = item.singleLine
        this.isEnabled = item.enabled
    }

    private fun TextInputLayout.bindIconStart(text: SelectTextField) {
        val mItem = text.cast<IHasItemStart>() ?: return
        if (mItem.hasItem) {
            this.setStartIconDrawable(mItem.resId)
        }

    }

    private fun TextInputEditText.setTextWithoutNotify(item: IInputLayoutField) {
        removeTextChangedListener(textWatcher)
        setText(item)
        addTextChangedListener(textWatcher)
    }
    private fun TextInputEditText.bindTextWithoutChange(item: IInputLayoutField) {
        item.cast<Signal>()?.bind {
            this.setText(item)
        }

    }
    private fun TextInputEditText.bindWithoutNotify(number: NumberFields) {
        number.cast<Signal>()?.bind {
            when (val valueHint = number.iHint){
                is IIntHint -> this.setHint(valueHint.hint)
                is ICharsHint -> this.hint = valueHint.hint
            }
            this.isEnabled = number.enabled
        }
    }





}







