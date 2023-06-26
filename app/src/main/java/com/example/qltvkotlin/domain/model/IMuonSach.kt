package com.example.qltvkotlin.domain.model

interface IMuonSach
interface IMuonSachItem : IMuonSach {
    val maDocGia: String
    val tenDocGia: String
    val tinhTrangThue: String
    val tongLoaiSach:String
    val soLuongThue: String
}