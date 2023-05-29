package com.example.qltvkotlin.domain.model


interface ISach

interface ISachItem {
    val imgSach: String
    val tenSach: String
    val tenTacGia: String
    val tong: String
    val conLai: String
}

interface ImageSach : IImage


interface IsSachSearch : IStringSearch {
    var mValueSach: String
}

