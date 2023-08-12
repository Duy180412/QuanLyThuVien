package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.presentation.widget.fields.PhotoField

interface ISachItem : IComponent {
    val maSach: String
    val imgSach: PhotoField
    val tenSach: String
    val tenTacGia: String
    val tong: String
    val conLai: String
}

