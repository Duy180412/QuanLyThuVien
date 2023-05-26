package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.domain.model.IStringSearch

interface ISach

interface ISachItem {
    val imgSach: String
    val tenSach: String
    val tenTacGia: String
    val tong: String
    val conLai: String
}



interface IsSachSearch : IStringSearch {
    val mValueSach: String
}

