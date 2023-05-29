package com.example.qltvkotlin.feature.main.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qltvkotlin.app.bindingOf
import com.example.qltvkotlin.databinding.ItemListRycviewBinding
import com.example.qltvkotlin.domain.model.ISachItem

class SachApdater(rvList: RecyclerView) : RecyclerView.Adapter<SachViewHolder>() {
    private var mList: List<ISachItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SachViewHolder {
        return SachViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: SachViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    init {
        rvList.adapter = this
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(mList: List<ISachItem>) {
        this.mList = mList
        notifyDataSetChanged()
    }
}

class SachViewHolder(
    parent: ViewGroup,
    private val binding: ItemListRycviewBinding = parent.bindingOf(ItemListRycviewBinding::inflate),
) : ViewHolder(binding.root) {
    fun bind(it: ISachItem) {
        binding.tenSach.text = it.tenSach
        binding.tenTacGia.text = it.tenTacGia
        binding.tongSach.text = it.tong
        binding.conLai.text = it.conLai
    }
}
