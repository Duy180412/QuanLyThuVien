package com.example.qltvkotlin.domain.repo

import com.example.qltvkotlin.datasource.roomdata.ThuVienDataRepo
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.feature.helper.Role

class MuonThueRepo {
    private val thuVien = ThuVienDataRepo.instance
    var listDemo = mutableListOf<IMuonSachItem>()


    suspend fun search(mKey: String, loaiSearch:Role): List<IMuonSachItem> {
        val keySearch = mKey.lowercase().trim()
        val filteredList = listDemo.filter { item ->
            when {
                keySearch.isNotBlank() && loaiSearch==Role.DangThue && item.tenDocGia.lowercase().contains(keySearch) -> item.tinhTrangThue.contains("Đang Thuê")
                keySearch.isBlank() && loaiSearch==Role.DangThue -> item.tinhTrangThue.contains("Đang Thuê")
                keySearch.isNotBlank() && loaiSearch==Role.HetHan && item.tenDocGia.lowercase().contains(keySearch) -> item.tinhTrangThue.contains("Hết Hạn")
                else -> keySearch.isBlank() && loaiSearch==Role.HetHan && item.tinhTrangThue.contains("Hết Hạn")
            }
        }
        return filteredList
}

init {
    val sach1 = object : IMuonSachItem {
        override val maDocGia = "112"
        override val tenDocGia = "Đỗ Xuân Duy"
        override val tinhTrangThue = "Đang Thuê"
        override val tongLoaiSach = "3"
        override val soLuongThue = "12"
    }
    val sach2 = object : IMuonSachItem {
        override val maDocGia = "113"
        override val tenDocGia = "Đỗ Thanh Duyên"
        override val tinhTrangThue = "Hết Hạn"
        override val tongLoaiSach = "3"
        override val soLuongThue = "12"
    }
    val sach3 = object : IMuonSachItem {
        override val maDocGia = "114"
        override val tenDocGia = "Lương Thị Lên"
        override val tinhTrangThue = "Đang Thuê"
        override val tongLoaiSach = "3"
        override val soLuongThue = "12"
    }
    val sach4 = object : IMuonSachItem {
        override val maDocGia = "115"
        override val tenDocGia = "Đỗ Văn Biên"
        override val tinhTrangThue = "Hết Hạn"
        override val tongLoaiSach = "3"
        override val soLuongThue = "12"
    }

    listDemo.add(sach1)
    listDemo.add(sach2)
    listDemo.add(sach3)
    listDemo.add(sach4)
}

companion object {
    val muonThueRepo = MuonThueRepo()
}
}