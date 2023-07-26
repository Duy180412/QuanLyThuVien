package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import com.example.qltvkotlin.domain.model.HasDate
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.model.getDateNow
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.widget.view.DateOnClick

class SelectDateInputLayoutViewHolder(parent: ViewGroup) : SelectInputLayoutViewHolder(parent) {
    override fun bind(item: IInputLayoutField) {
        super.bind(item)
        val originDate = item.cast<HasDate>()?.getDate() ?: getDateNow()
        binding.layoutText.setStartIconOnClickListener(DateOnClick(originDate) {
            item.update(it)
        })
    }
}