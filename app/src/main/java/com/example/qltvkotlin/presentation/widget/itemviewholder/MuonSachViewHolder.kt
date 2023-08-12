package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.ItemListMuonthueRycviewBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.enumeration.XoaMuonSach
import com.example.qltvkotlin.domain.model.Bindable
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.helper.AppStringResources

class MuonSachViewHolder(
    parent: ViewGroup,
    private val binding: ItemListMuonthueRycviewBinding = parent.bindingOf(
        ItemListMuonthueRycviewBinding::inflate
    )
) : RecyclerView.ViewHolder(binding.root), Bindable<IMuonSachItem>, HasCommandCallback {
    override var onCommand: (Command) -> Unit = {}
    private val appStringResources = AppStringResources.shared

    override fun bind(item: IMuonSachItem) {
        binding.tenDocGia.text = appStringResources(StringId.TenDocGia, item.tenDocGia)
        binding.trangThai.text = item.tinhTrangThue
        binding.slloaisach.text = appStringResources(StringId.Loai,item.tongLoaiSach)
        binding.tongsosach.text = appStringResources(StringId.TongMuon, item.soLuongThue)
        binding.btnDel.onClick{
            onCommand(XoaMuonSach(item))
        }
    }
}

