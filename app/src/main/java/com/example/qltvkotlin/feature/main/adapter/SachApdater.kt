package com.example.qltvkotlin.feature.main.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qltvkotlin.app.bindingOf
import com.example.qltvkotlin.databinding.ItemListRycviewBinding
import com.example.qltvkotlin.domain.model.ISachItem
import com.example.qltvkotlin.feature.presentation.extension.checkStringNull
import com.example.qltvkotlin.feature.presentation.extension.onClick

class SachApdater(rvList: RecyclerView) : RecyclerView.Adapter<SachApdater.SachViewHolder>() {
    private val mList = ArrayList<ISachItem>()
    lateinit var onClickItem: (String) -> Unit
    lateinit var onClickDel: (String) -> Unit
    private var backUpItemList: BackUpItemList<ISachItem>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SachViewHolder {
        return SachViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: SachViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.onClick { onClickItem(mList[position].maSach) }
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
    fun setList(mList: List<ISachItem>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class SachViewHolder(
        parent: ViewGroup,
        private val binding: ItemListRycviewBinding = parent.bindingOf(ItemListRycviewBinding::inflate),
    ) : ViewHolder(binding.root) {
        fun bind(position: Int) {
            val itemList = mList[position]
            binding.imageview.setAvatar(itemList.imgSach.getImage())
            binding.ten.text = itemList.tenSach.checkStringNull()
            binding.tenTacGia.text = itemList.tenTacGia.checkStringNull()
            binding.tongSach.text = itemList.tong.checkStringNull()
            binding.conLai.text = itemList.conLai.checkStringNull()
            binding.btnDel.onClick {
                onClickDel(itemList.maSach)
                backUpItemList = BackUpItemList(position, mList[position])
                mList.remove(itemList)
                notifyItemRemoved(position)
            }

        }
    }
}

class BackUpItemList<T>(val position: Int, val itemList: T)

