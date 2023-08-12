package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemListRycviewBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.OpenInfoDocGia
import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.enumeration.XoaDocGia
import com.example.qltvkotlin.domain.model.Bindable
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.helper.AppStringResources

class DocGiaViewHolder(
    parent: ViewGroup,
    private val binding: ItemListRycviewBinding = parent.bindingOf(ItemListRycviewBinding::inflate),
) : RecyclerView.ViewHolder(binding.root), Bindable<IDocGiaItem>, HasCommandCallback {
    override var onCommand: (Command) -> Unit = {}
    private val appStringResources = AppStringResources.shared
    override fun bind(item: IDocGiaItem) {
        binding.imageview.setAvatar(item.photoField.getImage())
        binding.ten.text = appStringResources(StringId.TenDocGia, item.tenDocGia)
        binding.info2.text = appStringResources(StringId.Sdt, item.sdt)
        binding.info3.text = appStringResources(StringId.HanDangKi,item.ngayHetHan)
        binding.info4.text = appStringResources(StringId.ChoThue, item.soLuongMuon)
        binding.btnDel.onClick {
            onCommand(XoaDocGia(item))
        }
        itemView.onClick{
            onCommand(OpenInfoDocGia(item))
        }
    }
}