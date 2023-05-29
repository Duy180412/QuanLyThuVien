package com.example.qltvkotlin.domain.model

interface IDocGia
interface IsDocGiaSearch : IStringSearch {
    var mValueDocGia: String
}

interface IImageDocGia : IImage
interface IDocGiaItem {
    val cmnd: String
    val tenDocGia: String
    val sdt: String
    val ngayHetHan: String
    val soLuongMuon: String
}
