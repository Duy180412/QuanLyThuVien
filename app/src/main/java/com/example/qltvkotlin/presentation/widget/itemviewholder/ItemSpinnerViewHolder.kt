package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemListSpinnerBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.SelectItemSpinner
import com.example.qltvkotlin.domain.model.Bindable
import com.example.qltvkotlin.domain.model.IItemSpinner
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.onClick

class ItemSpinnerViewHolder(
    parent: ViewGroup,
    private val binding: ItemListSpinnerBinding = parent.bindingOf(ItemListSpinnerBinding::inflate),
) : RecyclerView.ViewHolder(binding.root), Bindable<IItemSpinner>, HasCommandCallback {
    override var onCommand: (Command) -> Unit ={}
    override fun bind(item: IItemSpinner) {
        binding.name1.text = item.nameKey
        binding.name2.text = item.status
        if (item.status.contains("Hết Hạn") || item.status.contains("Đã Chọn")) {
            itemView.isEnabled = false
            itemView.alpha = 0.5f
        }
        itemView.onClick{
            onCommand(SelectItemSpinner(item))
        }
    }

}