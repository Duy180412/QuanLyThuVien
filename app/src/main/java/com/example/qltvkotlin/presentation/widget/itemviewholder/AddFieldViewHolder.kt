package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemAddFieldBinding
import com.example.qltvkotlin.domain.enumeration.AddFieldThemSachDangKi
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.model.Bindable
import com.example.qltvkotlin.domain.model.IAddView
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.onClick

class AddFieldViewHolder(
    parent: ViewGroup,
    binding:ItemAddFieldBinding = parent.bindingOf(ItemAddFieldBinding::inflate)
) :RecyclerView.ViewHolder(binding.root) , HasCommandCallback, Bindable<IAddView> {
    override var onCommand: (Command) -> Unit = {}
    override fun bind(item: IAddView) {
        itemView.onClick{
            onCommand(AddFieldThemSachDangKi())
        }
    }


}
