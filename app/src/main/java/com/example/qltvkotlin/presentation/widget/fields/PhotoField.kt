package com.example.qltvkotlin.presentation.widget.fields
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.IPhotoField
import com.example.qltvkotlin.domain.model.ImageEmpty
import com.example.qltvkotlin.domain.model.IsImageUrl
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.observable.signal

class PhotoField(private var images: IImage = ImageEmpty) : IPhotoField, Signal by signal() {
    override var iImage: IImage = images
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

    override val backUp: Any
        get() = images


    override fun hasChange(): Boolean {
      return  images is IsImageUri
    }
}


