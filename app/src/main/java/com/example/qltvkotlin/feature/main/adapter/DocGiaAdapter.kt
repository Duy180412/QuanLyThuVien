package com.example.qltvkotlin.feature.main.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qltvkotlin.app.bindingOf
import com.example.qltvkotlin.databinding.ItemListRycviewBinding
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.feature.presentation.extension.checkStringNull
import com.example.qltvkotlin.feature.presentation.extension.onClick

class DocGiaApdater(rvList: RecyclerView) : RecyclerView.Adapter<DocGiaApdater.DocGiaViewHolder>() {
    private val mList = ArrayList<IDocGiaItem>()
    lateinit var onClickItem: (String) -> Unit
    lateinit var onClickDel: (String) -> Unit
    private var backUpItemList: BackUpItemList<IDocGiaItem>? = null


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
        fun bind(position: Int) {
            val itemList = mList[position]
            binding.imageview.setAvatar(itemList.images.getImage())
            binding.ten.text = itemList.tenDocGia.checkStringNull()
            binding.tenTacGia.text = itemList.sdt.checkStringNull()
            binding.tongSach.text = itemList.ngayHetHan.checkStringNull()
            binding.conLai.text = itemList.soLuongMuon.checkStringNull()
            binding.btnDel.onClick {
                onClickDel(itemList.cmnd)
                backUpItemList = BackUpItemList(position, mList[position])
                mList.remove(itemList)
                notifyItemRemoved(position)
            }

        }
    }

}


