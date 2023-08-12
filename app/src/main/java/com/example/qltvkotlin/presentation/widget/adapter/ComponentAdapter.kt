package com.example.qltvkotlin.presentation.widget.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.model.Bindable
import com.example.qltvkotlin.domain.model.IAddView
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IDateField
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.IHorizontalLine
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.domain.model.IPhoneNumberField
import com.example.qltvkotlin.domain.model.IPhotoField
import com.example.qltvkotlin.domain.model.ISachItem
import com.example.qltvkotlin.domain.model.ISelectTextField
import com.example.qltvkotlin.domain.model.ITextInputLayoutField
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.widget.itemviewholder.DocGiaViewHolder
import com.example.qltvkotlin.presentation.widget.itemviewholder.AddFieldViewHolder
import com.example.qltvkotlin.presentation.widget.itemviewholder.CustomFieldsViewHolder
import com.example.qltvkotlin.presentation.widget.itemviewholder.HorizontalLineDecorationViewHolder
import com.example.qltvkotlin.presentation.widget.itemviewholder.MuonSachViewHolder
import com.example.qltvkotlin.presentation.widget.itemviewholder.PhotoViewHolder
import com.example.qltvkotlin.presentation.widget.itemviewholder.SachViewHolder
import com.example.qltvkotlin.presentation.widget.itemviewholder.SelectDateInputLayoutViewHolder
import com.example.qltvkotlin.presentation.widget.itemviewholder.SelectTextInputLayoutViewHolder
import com.example.qltvkotlin.presentation.widget.itemviewholder.TextEnterLayoutViewHolder
import java.util.concurrent.atomic.AtomicInteger

class ComponentAdapter(rycView: RecyclerView) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    HasCommandCallback, Signal.Closable by Signal.Bags() {

    override var onCommand: (Command) -> Unit = {}
    private var mList: List<IComponent>? = null

    companion object {
        private val index = AtomicInteger()
        val TYPE_PHOTO = index.getAndIncrement()
        val TYPE_DATE = index.getAndIncrement()
        val TYPE_TEXTLAYOUT = index.getAndIncrement()
        val TYPE_TEXTLAYOUTSELECT = index.getAndIncrement()
        val HORIZONTALLINE = index.getAndIncrement()
        val TYPE_ADDITEMEMYPTY = index.getAndIncrement()
        val TYPE_SELECTTEXTANDNUMBER = index.getAndIncrement()
        val TYPE_SACHITEM = index.getAndIncrement()
        val TYPE_DOCGIAITEM = index.getAndIncrement()
        val TYPE_MUONSACHITEM = index.getAndIncrement()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TEXTLAYOUT -> TextEnterLayoutViewHolder(parent)
            TYPE_PHOTO -> PhotoViewHolder(parent)
            TYPE_DATE -> SelectDateInputLayoutViewHolder(parent)
            TYPE_TEXTLAYOUTSELECT -> SelectTextInputLayoutViewHolder(parent)
            HORIZONTALLINE -> HorizontalLineDecorationViewHolder(parent)
            TYPE_ADDITEMEMYPTY -> AddFieldViewHolder(parent)
            TYPE_SELECTTEXTANDNUMBER -> CustomFieldsViewHolder(parent)
            TYPE_SACHITEM -> SachViewHolder(parent)
            TYPE_DOCGIAITEM -> DocGiaViewHolder(parent)
            TYPE_MUONSACHITEM -> MuonSachViewHolder(parent)
            else -> error("Sai")
        }
    }


    init {
        rycView.adapter = this
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        close()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<IComponent>?) {
        close()
        list.cast<Signal>()?.bind { notifyDataSetChanged() }
        mList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (mList!![position]) {
            is ITextInputLayoutField -> TYPE_TEXTLAYOUT
            is IPhoneNumberField -> TYPE_TEXTLAYOUT
            is IPhotoField -> TYPE_PHOTO
            is IDateField -> TYPE_DATE
            is IHorizontalLine -> HORIZONTALLINE
            is ISelectTextField -> TYPE_TEXTLAYOUTSELECT
            is IAddView -> TYPE_ADDITEMEMYPTY
            is IFieldsCustom -> TYPE_SELECTTEXTANDNUMBER
            is ISachItem -> TYPE_SACHITEM
            is IDocGiaItem -> TYPE_DOCGIAITEM
            is IMuonSachItem -> TYPE_MUONSACHITEM
            else -> error("Not Sp")
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        holder.cast<AutoCloseable>()?.close()
    }


    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HasCommandCallback) holder.onCommand = { this.onCommand(it) }
        holder.cast<AutoCloseable>()?.close()
        holder.cast<Bindable<IComponent>>()?.bind(mList!![position])
    }

}
