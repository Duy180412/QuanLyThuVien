package com.example.qltvkotlin.data.model


data class MuonThue(
    val cmndDocGia: String
) {
    val danhSachMuon = mutableListOf<ThongTinThue>()
}


data class ThongTinThue(
    val maSach: String,
    var soLuongMuon: Int
)