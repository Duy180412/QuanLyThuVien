package com.example.qltvkotlin.presentation.feature.main.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.databinding.ItemListRycviewBinding
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.presentation.extension.checkStringNull
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.widget.HorizontalLineDecoration

class DocGiaApdater(rvList: RecyclerView) : RecyclerView.Adapter<DocGiaApdater.DocGiaViewHolder>() {
    private val mList = ArrayList<IDocGiaItem>()
    lateinit var onClickItem: (String) -> Unit
    lateinit var onClickDel: (String) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocGiaViewHolder {
        return DocGiaViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: DocGiaViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.onClick { onClickItem(mList[position].cmnd) }
    }

    init {
        rvList.adapter = this
        rvList.addItemDecoration(HorizontalLineDecoration.item)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(mList: List<IDocGiaItem>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class DocGiaViewHolder(
        parent: ViewGroup,
        private val binding: ItemListRycviewBinding = parent.bindingOf(ItemListRycviewBinding::inflate),
    ) : ViewHolder(binding.root) {
        private val resources = itemView.resources
        fun bind(position: Int) {
            val itemList = mList[position]
            binding.imageview.setAvatar(itemList.photoField.getImage())
            binding.ten.text = resources.getString(R.string.view_ten_docgia,itemList.tenDocGia.checkStringNull() )
            binding.tenTacGia.text =  resources.getString(R.string.view_sdt,itemList.sdt.checkStringNull() )
            binding.tongSach.text = resources.getString(R.string.view_han_dangki,itemList.ngayHetHan.checkStringNull() )
            binding.choThue.text = resources.getString(R.string.view_dangthue,itemList.soLuongMuon.checkStringNull() )
            binding.btnDel.onClick {
                onClickDel(itemList.cmnd)
            }

        }
    }

}


