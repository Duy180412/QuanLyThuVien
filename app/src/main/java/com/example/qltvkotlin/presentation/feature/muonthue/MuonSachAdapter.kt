package com.example.qltvkotlin.presentation.feature.muonthue

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.databinding.ItemListMuonthueRycviewBinding
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.presentation.extension.checkStringNull
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.widget.HorizontalLineDecoration

class MuonSachApdater(rvList: RecyclerView) :
    RecyclerView.Adapter<MuonSachApdater.MuonSachViewHolder>() {
    private val mList = ArrayList<IMuonSachItem>()
    lateinit var onClickItem: (String) -> Unit
    lateinit var onClickDel: (String) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuonSachViewHolder {
        return MuonSachViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MuonSachViewHolder, position: Int) {
        holder.bind(position)
//        holder.itemView.onClick { onClickItem(mList[position].maDocGia) }
    }

    init {
        rvList.adapter = this
        rvList.addItemDecoration(HorizontalLineDecoration.item)
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setList(mList: List<IMuonSachItem>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class MuonSachViewHolder(
        parent: ViewGroup,
        private val binding: ItemListMuonthueRycviewBinding = parent.bindingOf(
            ItemListMuonthueRycviewBinding::inflate
        )
    ) : ViewHolder(binding.root) {
        private val resources = itemView.resources
        fun bind(position: Int) {

            val itemList = mList[position]
            binding.tenDocGia.text = resources.getString(R.string.view_ten_docgia,itemList.tenDocGia.checkStringNull() )
            binding.trangThai.text = itemList.tinhTrangThue
            binding.slloaisach.text = resources.getString(R.string.view_tong_loai,itemList.tongLoaiSach.checkStringNull() )
            binding.tongsosach.text = resources.getString(R.string.view_tong_muon,itemList.soLuongThue.checkStringNull() )
            binding.btnDel.onClick{
                onClickDel(itemList.maDocGia)
            }
        }
    }


}


