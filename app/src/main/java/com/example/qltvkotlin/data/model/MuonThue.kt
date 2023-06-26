package com.example.qltvkotlin.data.model


data class MuonThue(
    val docGiaDTO: DocGiaDTO
) {
    val danhSachMuon = mutableListOf<ThongTinThue>()
}


data class ThongTinThue(
    val sachDTO: SachDTO,
    var soLuongMuon: Int
)