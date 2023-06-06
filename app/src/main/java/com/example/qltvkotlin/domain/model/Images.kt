package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.domain.observable.Signal

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
