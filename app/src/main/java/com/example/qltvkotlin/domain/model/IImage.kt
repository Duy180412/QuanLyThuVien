package com.example.qltvkotlin.domain.model

import android.net.Uri

interface IImage

interface IsImageUri : IImage {
    var uriImage: Uri
}

interface IsImageUrl : IImage {
    var urlImage: String
}

interface GetImage {
    fun getImage(): IImage?
}
interface IsImageUrlStart:IImage{
    var urlImage: String
}

interface IsImageEmpty : IImage
