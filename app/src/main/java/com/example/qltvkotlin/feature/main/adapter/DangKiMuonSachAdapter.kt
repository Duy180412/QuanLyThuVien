package com.example.qltvkotlin.feature.main.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qltvkotlin.app.bindingOf
import com.example.qltvkotlin.databinding.ItemlastaddbookBinding
import com.example.qltvkotlin.databinding.ItemlistAddmuonsachBinding
import com.example.qltvkotlin.domain.model.IThongTinSachThueGeneral
import com.example.qltvkotlin.domain.model.ThongTinSachThueSet
import com.example.qltvkotlin.domain.model.bindOnChange
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.feature.main.help.Command
import com.example.qltvkotlin.feature.main.help.HasCommandCallback
import com.example.qltvkotlin.feature.main.help.ThemSachCmd
import com.example.qltvkotlin.feature.main.help.ThemThemSachRongCmd
import com.example.qltvkotlin.feature.main.help.XoaSachCmd
import com.example.qltvkotlin.feature.presentation.extension.bindTo
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.onClick

class DangKiMuonSachAdapter(rycView: RecyclerView) : RecyclerView.Adapter<ViewHolder>(),
    HasCommandCallback {
    private var listChangeClosable: AutoCloseable? = null
    private var mList = emptyList<IThongTinSachThueGeneral>()
    override var onCommand: (Command) -> Unit = {}


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
    fun setList(list: List<IThongTinSachThueGeneral>) {
        mList = list
        listChangeClosable?.close()
        listChangeClosable = list.cast<Signal>()?.subscribe { notifyDataSetChanged() }
        notifyDataSetChanged()
    }

    inner class ItemAddMuonThueViewHodel(
        val parent: ViewGroup,
        val binding: ItemlistAddmuonsachBinding = parent.bindingOf(ItemlistAddmuonsachBinding::inflate)
    ) : ViewHolder(binding.root), Signal.Closable by Signal.Bags() {
        private val owner = itemView.context as LifecycleOwner
        fun bind(position: Int) {
            val item = mList[position].cast<IThongTinSachThueGeneral>()!!
            binding.themsachNhap.setText(item.tenSach.toString())
            binding.soluongNhap.setText(item.soLuong.toString())
            val thongTinSachThue = item.cast<ThongTinSachThueSet>() ?: return
            binding.soluongNhap.bindTo { thongTinSachThue.soLuong }
            thongTinSachThue.tenSach.also { it2 ->
                checkAndShowError(it2, binding.themsach)
                bindOnChange(owner, it2) {
                    checkAndShowError(it, binding.themsach)
                }
            }


            thongTinSachThue.soLuong.also { it ->
                checkAndShowError(it, binding.soluong)
                bindOnChange(owner, it) {
                    checkAndShowError(it, binding.soluong)
                }
            }

            binding.themsach.setStartIconOnClickListener {
                onCommand(ThemSachCmd(thongTinSachThue))
            }

            binding.btnDel.onClick {
                onCommand(XoaSachCmd(item))
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
                onCommand(ThemThemSachRongCmd())
            }
        }
    }

}