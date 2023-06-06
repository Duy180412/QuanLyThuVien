package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.datasource.bo.SachEdit


interface ISach

interface ISachItem {
    val imgSach: Images
    val tenSach: String
    val tenTacGia: String
    val tong: String
    val conLai: String
}
interface ISachChange : ISach

interface ISachReadOnly {
    val maSach: String
    val imageSach: Images
    val tenSach: String
    val loaiSach: String
    val tenTacGia: String
    val nhaXuatBan: String
    val namXuatBan: String
    val tongSach: String
    val choThue: String
}
interface ISachEditable {
    val iSachRead:ISachReadOnly
    var maSach: Chars
    var imageSach: Images
    var tenSach: Chars
    var loaiSach: Chars
    var tenTacGia: Chars
    var nhaXuatBan: Chars
    var namXuatBan: Chars
    var tongSach: Chars
    var choThue: Chars
}

interface ImageSach : IImage


interface IsSachSearch : IStringSearch {
    var mValueSach: String
}

