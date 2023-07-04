package com.example.qltvkotlin.feature.main.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qltvkotlin.app.bindingOf
import com.example.qltvkotlin.databinding.ItemlastaddbookBinding
import com.example.qltvkotlin.databinding.ItemlistAddmuonsachBinding
import com.example.qltvkotlin.domain.model.IThongTinSachThueCreate
import com.example.qltvkotlin.domain.model.ThongTinSachThueSet
import com.example.qltvkotlin.domain.model.bindCharOwner
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.observable.signalBags
import com.example.qltvkotlin.feature.main.help.dialogcustom.SachSelecDialogOnwer
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.onClick

class DangKiMuonSachAdapter(rycView: RecyclerView) : RecyclerView.Adapter<ViewHolder>() {
    private val mList = ArrayList<IThongTinSachThueCreate>()

    companion object {
        private const val VIEW_TYPE_NORMAL = 0
        private const val VIEW_TYPE_LAST = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LAST -> AddSachMuonViewHolder(parent)
            else -> ItemAddMuonThueViewHodel(parent)

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cast<AutoCloseable>()?.close()
        when (holder) {
            is ItemAddMuonThueViewHodel -> holder.bind(position)
            is AddSachMuonViewHolder -> holder.bind()
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.cast<AutoCloseable>()?.close()
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
    fun setList(list: List<IThongTinSachThueCreate>) {
        mList.clear()
        mList.addAll(list)
        if (list.isEmpty() || list is MutableList) mList.add(mList.size, ThongTinSachThueSet())
        notifyDataSetChanged()
    }

    inner class ItemAddMuonThueViewHodel(
        val parent: ViewGroup,
        val binding: ItemlistAddmuonsachBinding = parent.bindingOf(ItemlistAddmuonsachBinding::inflate)
    ) : ViewHolder(binding.root), SachSelecDialogOnwer, Signal.Closable by signalBags() {
        private val owner = itemView.context as LifecycleOwner
        fun bind(position: Int) {
            val item = mList[position].cast<IThongTinSachThueCreate>()!!
            binding.themsachNhap.setText(item.tenSach.toString())
            binding.soluongNhap.setText(item.soLuong.toString())
            val thongTinSachThue = item.cast<ThongTinSachThueSet>() ?: return

            binding.themsach.setStartIconOnClickListener {
                sachDialog.showDialog {
                    thongTinSachThue.maSach.update(it.key)
                    thongTinSachThue.tenSach.update(it.nameKey)
                    val value2 = mList[0].tenSach
                    val value3 = mList[1].tenSach
                    val value = thongTinSachThue.tenSach
                    thongTinSachThue.soLuong.setMax(it.status)
                }
            }
            thongTinSachThue.tenSach.also { it2 ->
                val value2 = mList[0].tenSach
                val value3 = mList[1].tenSach
                val it = it2
                checkAndShowError(it2, binding.themsach)
                bindCharOwner(owner, it2) {
                    checkAndShowError(it, binding.themsach)
                }
            }
            thongTinSachThue.soLuong.also { it ->
                checkAndShowError(it, binding.soluong)
                bindCharOwner(owner, it) {
                    checkAndShowError(it, binding.soluong)
                }
            }
            binding.btnDel.onClick {
                mList.removeAt(position)
                notifyItemRemoved(position)
            }
            thongTinSachThue.tenSach.cast<Signal>()?.bind {
                binding.themsachNhap.setText(thongTinSachThue.tenSach)
            }
        }

    }

    inner class AddSachMuonViewHolder(
        parent: ViewGroup,
        private val binding: ItemlastaddbookBinding = parent.bindingOf(
            ItemlastaddbookBinding::inflate
        ),
    ) : ViewHolder(binding.root) {
        fun bind() {
            binding.addSach.onClick {
                mList.add(mList.size - 1, ThongTinSachThueSet())
                notifyItemInserted(mList.size - 1)
                val value2 = mList[0].tenSach
                val value3 = mList[1].tenSach
            }

        }
    }
}