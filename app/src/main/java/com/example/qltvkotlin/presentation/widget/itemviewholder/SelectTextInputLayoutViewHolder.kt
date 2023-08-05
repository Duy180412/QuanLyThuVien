package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import com.example.qltvkotlin.domain.enumeration.SelectDocGiaMuonSach
import com.example.qltvkotlin.domain.model.IInputLayoutField

class SelectTextInputLayoutViewHolder(
    parent: ViewGroup
) : SelectInputLayoutViewHolder(parent) {

    override fun bind(item: IInputLayoutField) {
        super.bind(item)
        binding.layoutText.setStartIconOnClickListener {
            onCommand(SelectDocGiaMuonSach(item))
        }
    }


}