package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.data.model.ThongTinThue

interface IMuonSach
interface IMuonSachItem : IMuonSach {
    val maDocGia: String
    val tenDocGia: String
    val tinhTrangThue: String
    val tongLoaiSach: String
    val soLuongThue: String
}

interface IMuonSachSet:IMuonSachCreate
interface IMuonSachGet:IMuonSachCreate
interface IMuonSachCreate : IMuonSach{
    val docGia:IDocGiaItem
    val list:List<ThongTinThue>
}