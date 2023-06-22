package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.feature.presentation.extension.cast

class Images(images: IImage) : IImage, Updatable, HasIsValid, Validable, GetImage,
    Signal by Signal.MultipleSubscription() {

    private var iImage: IImage = images

    override fun update(value: Any?) {
        this.iImage = ((value as? IImage)!!)
        emit()
    }

    override fun validate(): Boolean {
        return this.isValid
    }

    override val isValid: Boolean
        get() = iImage is IsImageUrl || iImage is IsImageUri

    override fun getImage(): IImage {
        return this.iImage
    }
}


fun Images.getUrlFromImages(): String {
    return this.cast<IsImageUrl>()?.urlImage.orEmpty()
}
fun CharSequence?.createImagesFromUrl(): Images = Images(createUrlFromString())


fun CharSequence?.createUrlFromString(): IImage {
    return if (this?.isNotEmpty() == true) {
        object : IsImageUrl {
            override var urlImage = this@createUrlFromString.toString()
        }
    } else object : IsImageEmpty {}
}

