package com.example.qltvkotlin.feature.main.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qltvkotlin.app.bindingOf
import com.example.qltvkotlin.databinding.ItemListMuonthueRycviewBinding
import com.example.qltvkotlin.databinding.ItemlistAddmuonsachBinding
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.feature.presentation.extension.onClick

class MuonSachApdater(rvList: RecyclerView) :
    RecyclerView.Adapter<MuonSachApdater.MuonSachViewHolder>() {
    private val mList = ArrayList<IMuonSachItem>()
    lateinit var onClickItem: (String) -> Unit
    lateinit var onClickDel: (String) -> Unit
    private var backUpItemList: BackUpItemList<IMuonSachItem>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuonSachViewHolder {
        return MuonSachViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MuonSachViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.onClick { onClickItem(mList[position].maDocGia) }
    }

    init {
        rvList.adapter = this
    }

    fun unDoItemList() {
        if (backUpItemList == null) return
        if (itemCount < backUpItemList!!.position) return
        else {
            mList.add(backUpItemList!!.position, backUpItemList!!.itemList)
            notifyItemInserted(backUpItemList!!.position)
        }
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
        fun bind(position: Int) {
            val itemList = mList[position]
            binding.tenDocGia.text = itemList.tenDocGia
            binding.trangThai.text = itemList.tinhTrangThue
            binding.slloaisach.text = itemList.tongLoaiSach
            binding.tongsosach.text = itemList.soLuongThue
            binding.btnDel.onClick{
                onClickDel(itemList.maDocGia)
                mList.remove(itemList)
                notifyItemRemoved(position)
            }
        }
    }


}


