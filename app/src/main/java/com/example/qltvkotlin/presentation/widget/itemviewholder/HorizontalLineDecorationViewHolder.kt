package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.HorizontallineBinding
import com.example.qltvkotlin.presentation.extension.bindingOf

class HorizontalLineDecorationViewHolder(
    parent: ViewGroup,
    private val binding: HorizontallineBinding = parent.bindingOf(HorizontallineBinding::inflate)
) : RecyclerView.ViewHolder(binding.root)