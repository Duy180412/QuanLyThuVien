package com.example.qltvkotlin.feature.helper.spinner

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.app.bindingOf
import com.example.qltvkotlin.databinding.ItemListSpinnerBinding
import com.example.qltvkotlin.feature.presentation.extension.onClick

class AdapterSpinnerCustom(rycView: RecyclerView) :
    RecyclerView.Adapter<AdapterSpinnerCustom.ItemSpinnerViewHolder>() {
    private val mList = ArrayList<IItemSpinner>()
    lateinit var onClickItem: (String) -> Unit


    inner class ItemSpinnerViewHolder(
        parent: ViewGroup,
        private val binding: ItemListSpinnerBinding = parent.bindingOf(ItemListSpinnerBinding::inflate),
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val itemList = mList[position]
            binding.name1.text = itemList.nameKey
            binding.name2.text = itemList.status
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSpinnerViewHolder {
        return ItemSpinnerViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(mList: List<IItemSpinner>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemSpinnerViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.onClick { onClickItem(mList[position].key) }
    }

    init {
        rycView.adapter = this
    }
}