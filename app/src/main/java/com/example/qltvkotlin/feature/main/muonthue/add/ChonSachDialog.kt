package com.example.qltvkotlin.feature.main.muonthue.add

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.DialogChonSachBinding
import com.example.qltvkotlin.databinding.ItemListSpinnerBinding
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.feature.helper.spinner.IItemSpinner
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.onClick

class ChonSachDialog(context: Context) : Dialog(context) {

    private val binding = DialogChonSachBinding.inflate(LayoutInflater.from(context), null, false)

    init {
        setContentView(binding.root)
        binding.rvList.adapter = Adapter()
    }

    fun show(items: List<IItemSpinner>, onItemClick: (IItemSpinner) -> Unit) {
        binding.rvList.adapter.cast<Adapter>()?.apply {
            this.onItemClick = {
                onItemClick(it)
                dismiss()
            }
        }?.submit(items)
        super.show()
    }

    class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var onItemClick: (IItemSpinner) -> Unit = {}
        var items: List<IItemSpinner> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding = ItemListSpinnerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return object : RecyclerView.ViewHolder(binding.root), Updatable {
                override fun update(value: Any?) {
                    if (value !is IItemSpinner) return
                    binding.name1.text = value.nameKey
                    itemView.onClick {
                        onItemClick(value)
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.cast<Updatable>()?.update(items[position])
        }

        @SuppressLint("NotifyDataSetChanged")
        fun submit(items: List<IItemSpinner>) {
            this.items = items
            notifyDataSetChanged()
        }

    }
}