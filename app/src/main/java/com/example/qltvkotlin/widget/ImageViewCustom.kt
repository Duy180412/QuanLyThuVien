package com.example.qltvkotlin.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.model.GetImage
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.domain.model.IsImageUrl

class ImageViewCustom : AppCompatImageView {
    private var avatarTron = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {
        if (attrs != null) {
            val typedArray: TypedArray =
                context.obtainStyledAttributes(attrs, R.styleable.ImageViewCustom)
            avatarTron = typedArray.getBoolean(R.styleable.ImageViewCustom_circleCrop, false)
            typedArray.recycle()
        }
    }


     fun setAvatar(iImage: IImage?) {
        val glide: RequestManager = Glide.with(this)
        var builder: RequestBuilder<Drawable?>?
        if (iImage is GetImage) {
            setAvatar(iImage.getImage())
            return
        }
            builder = when (iImage) {
            is IsImageUri -> glide.load(iImage.uriImage)
            is IsImageUrl -> glide.load(iImage.urlImage)
            else -> glide.load(R.drawable.avatardemo)
        }
        if (avatarTron) builder = builder.transform(CircleCrop())
        builder = builder.error(R.drawable.errorloadimg)
        builder = builder.skipMemoryCache(true)
        builder = builder.diskCacheStrategy(DiskCacheStrategy.NONE)
        builder.into(this)
    }
}