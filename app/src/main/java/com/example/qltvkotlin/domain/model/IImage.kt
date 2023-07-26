package com.example.qltvkotlin.domain.model

import android.net.Uri

interface IImage

interface IsImageUri : IImage {
    var uriImage: Uri
}

interface IsImageUrl : IImage {
    var urlImage: String
}

interface HasImage {
    fun getImage(): IImage?
}

object ImageEmpty : IImage
interface HasIImagse {
    val iImage: IImage
}
