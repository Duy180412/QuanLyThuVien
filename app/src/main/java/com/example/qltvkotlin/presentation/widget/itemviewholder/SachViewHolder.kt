package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemListRycviewBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.XoaSach
import com.example.qltvkotlin.domain.enumeration.OpenInfoSach
import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.model.Bindable
import com.example.qltvkotlin.domain.model.ISachItem
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.helper.AppStringResources

class SachViewHolder(
    parent: ViewGroup,
    private val binding: ItemListRycviewBinding = parent.bindingOf(ItemListRycviewBinding::inflate),
) : RecyclerView.ViewHolder(binding.root), Bindable<ISachItem>, HasCommandCallback {
    private val appStringResources = AppStringResources.shared
    override var onCommand: (Command) -> Unit = {}
    override fun bind(item: ISachItem) {
        binding.imageview.setAvatar(item.imgSach.getImage())
        binding.ten.text = appStringResources(StringId.TenSach,item.tenSach)
        binding.info2.text = appStringResources(StringId.TenTacGia,item.tenTacGia)
        binding.info3.text = appStringResources(StringId.TongSach,item.tong)
        binding.info4.text = appStringResources(StringId.ConLai, item.conLai)
        binding.btnDel.onClick { onCommand(XoaSach(item)) }
        itemView.onClick { onCommand(OpenInfoSach(item)) }
    }


}