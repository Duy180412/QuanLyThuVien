package com.example.qltvkotlin.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.ImageDefault
import com.example.qltvkotlin.domain.model.IsImageUrL
import com.example.qltvkotlin.domain.model.IsImageUri

class ImageViewCustom(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    test: () -> Unit
) :
    AppCompatImageView(context, attrs, defStyleAttr) {
    private var avatarTron = false


    init {
        test.apply {
            if (attrs != null) {
                val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageViewCustom)
                avatarTron = typedArray.getBoolean(R.styleable.ImageViewCustom_circleCrop, false)
                typedArray.recycle()
            }
        }
    }

//    fun setAvatar(iImage: IImage) {
//        val glide: RequestManager = Glide.with(this)
//        var builder: RequestBuilder<Drawable?>? = null
//        if (iImage is IGetImage) {
//            val iImage1: IImage = (iImage as IGetImage).getImage()
//            setAvatar(iImage1)
//            return
//        }
//        if (iImage is IsImageUri) {
//            builder = glide.load(iImage.uriImage)
//        } else if (iImage is IsImageUrL) {
//            val url: String = iImage.urlImage
//            Log.v("urlload", url)
//            builder = glide.load(url)
//        } else if (iImage is ImageDefault) {
//            builder = glide.load((iImage as ImageDefault).imageDefault)
//        } else if (iImage is IImageBitMap) {
//            builder = glide.load((iImage as IImageBitMap).getBitmap())
//        }
//        if (builder == null) return
//        if (avatarTron) builder = builder.transform(CircleCrop())
//        builder = builder.error(R.drawable.errorloadimg)
//        builder = builder.skipMemoryCache(true)
//        builder = builder.diskCacheStrategy(DiskCacheStrategy.NONE)
//        builder.into(this)
//    }
}