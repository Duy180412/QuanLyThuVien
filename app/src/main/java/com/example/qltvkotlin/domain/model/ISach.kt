package com.example.qltvkotlin.domain.model


interface ISach

interface ISachItem {
    val imgSach: String
    val tenSach: String
    val tenTacGia: String
    val tong: String
    val conLai: String
}
interface ISachEdit{
    val maSach:CharSequence
    val imageSach:IImage
    val tenSach:CharSequence
    var loaiSach:String
    var tenTacGia:String
    var nhaXuatBan:String
    var namXuatBan:String
    var tongSach:String
    var choThue:String
}

interface ImageSach : IImage


interface IsSachSearch : IStringSearch {
    var mValueSach: String
}

