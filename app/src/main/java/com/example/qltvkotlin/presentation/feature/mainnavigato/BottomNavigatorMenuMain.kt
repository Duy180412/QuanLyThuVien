package com.example.qltvkotlin.presentation.feature.mainnavigato

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.databinding.ItemTabMainBinding
import com.example.qltvkotlin.presentation.extension.onClick

class BottomNavigatorMenuMain(private val viewGroup: LinearLayout) {
    private var mOnItemSelectedListener: (id: Int) -> Boolean = { false }
    private var context = viewGroup.context
    private var mHolders = ArrayList<ViewHolder>()
    val selectedItemId: Int get() = (mHolders.find { it.isSelected } ?: mHolders.first()).item.title

    private val menuItems = listOf(
        MenuItemView(R.drawable.ic_docgia, R.string.account),
        MenuItemView(R.drawable.ic_book, R.string.sach),
        MenuItemView(R.drawable.ic_docgia, R.string.doc_gia),
        MenuItemView(R.drawable.ic_muonthue, R.string.muon_thue)
    )

    init {
        menuItems.forEach {
            val viewHolder = onCreateViewHodel(it)
            mHolders.add(viewHolder)
            viewGroup.addView(
                viewHolder.view,
                LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
            )
        }
        setSelected(null)
    }

    fun setSelected(it: View?) {
        for (mHoder in mHolders) {
            mHoder.isSelected = it == mHoder.view
        }
        if (it == null) mHolders.first().isSelected = true
    }

    private fun onCreateViewHodel(it: MenuItemView): ViewHolder = SimpleViewHolder(it)


    fun setOnItemSelectedListener(function: (id: Int) -> Boolean) {
        mOnItemSelectedListener = function
    }

    inner class SimpleViewHolder(it: MenuItemView) : ViewHolder {
        private val binding = viewGroup.bindingOf(ItemTabMainBinding::inflate)
        override val item = it
        override val view = binding.root
        override var isSelected: Boolean = false
            set(value) {
                field = value
                binding.txtTabName.isSelected = value
                binding.imgTab.isSelected = value
            }


        init {
            binding.txtTabName.text = context.getString(item.title)
            binding.imgTab.setImageResource(item.icon)
            binding.root.onClick {
                if (mOnItemSelectedListener(item.title)) setSelected(it)
            }
        }
    }

    interface ViewHolder {
        val item: MenuItemView
        val view: View
        var isSelected: Boolean
    }


}


class MenuItemView(val icon: Int, val title: Int)

