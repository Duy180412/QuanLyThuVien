package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.presentation.widget.fields.PhotoField


interface IDocGiaItem : IComponent {
    val cmnd: String
    val photoField: PhotoField
    val tenDocGia: String
    val sdt: String
    val ngayHetHan: String
    val soLuongMuon: String
}

