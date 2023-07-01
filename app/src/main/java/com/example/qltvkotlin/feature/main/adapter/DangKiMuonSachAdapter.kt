package com.example.qltvkotlin.feature.main.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qltvkotlin.app.bindingOf
import com.example.qltvkotlin.data.model.ThongTinThue
import com.example.qltvkotlin.databinding.ItemListMuonthueRycviewBinding
import com.example.qltvkotlin.databinding.ItemlastaddbookBinding
import com.example.qltvkotlin.databinding.ItemlistAddmuonsachBinding
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.feature.presentation.extension.onClick
import kotlinx.coroutines.NonDisposableHandle.parent

class DangKiMuonSachAdapter(rycView: RecyclerView) : RecyclerView.Adapter<ViewHolder>() {
    lateinit var openDialog:()->Unit
    private val mList = ArrayList<ThongTinThue>()
    companion object{
        private const val VIEW_TYPE_NORMAL = 0
        private const val VIEW_TYPE_LAST = 1
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            VIEW_TYPE_LAST -> AddSachMuonViewHolder(parent)
            else -> ItemAddMuonThueViewHodel(parent)

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ItemAddMuonThueViewHodel -> holder.bind(position)
            is AddSachMuonViewHolder -> holder.bind(position)
        }
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mList.size - 1) {
            VIEW_TYPE_LAST
        } else {
            VIEW_TYPE_NORMAL
        }
    }
    init {
        rycView.adapter = this
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(mList: List<ThongTinThue>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class ItemAddMuonThueViewHodel(
        val parent: ViewGroup,
        val binding: ItemlistAddmuonsachBinding= parent.bindingOf(ItemlistAddmuonsachBinding::inflate)
    ) : ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.themsach.setStartIconOnClickListener { openDialog() }
        }

    }
    inner class AddSachMuonViewHolder(
        parent: ViewGroup,
        private val binding: ItemlastaddbookBinding = parent.bindingOf(
            ItemlastaddbookBinding::inflate
        ),
    ) : ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.addSach.onClick{
                mList.add(mList.size -1,ThongTinThue("",0))
                notifyItemInserted(mList.size -1)
            }

        }
    }
}