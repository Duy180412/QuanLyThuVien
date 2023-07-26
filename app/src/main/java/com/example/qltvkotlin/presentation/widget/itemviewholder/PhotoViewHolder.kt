package com.example.qltvkotlin.presentation.widget.itemviewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.databinding.ItemPhotoViewBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.SelecPhoto
import com.example.qltvkotlin.domain.model.Bindable
import com.example.qltvkotlin.domain.model.IPhotoField
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.onClick

class PhotoViewHolder(
    parent: ViewGroup,
    private val binding: ItemPhotoViewBinding = parent.bindingOf(ItemPhotoViewBinding::inflate),
) : RecyclerView.ViewHolder(binding.root), Bindable<IPhotoField>, HasCommandCallback,
    Signal.Closable by Signal.Bags() {
    override var onCommand: (Command) -> Unit = {}
    override fun bind(item: IPhotoField) {
        binding.avatar.setAvatar(item.iImage)
        binding.avatar.onClick {
            onCommand(SelecPhoto(item))
        }
        item.cast<Signal>()?.bind { binding.avatar.setAvatar(item.iImage) }
    }


}