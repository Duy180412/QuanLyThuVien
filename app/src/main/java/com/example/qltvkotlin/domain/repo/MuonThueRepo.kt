package com.example.qltvkotlin.domain.repo

import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.MuonThue
import com.example.qltvkotlin.data.model.ThongTinThue
import com.example.qltvkotlin.datasource.roomdata.ThuVienDataRepo
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.domain.model.ThongTinSachThueSet
import com.example.qltvkotlin.domain.model.getDateNow
import com.example.qltvkotlin.feature.helper.Role
import com.example.qltvkotlin.feature.presentation.extension.dateFromString
import java.util.Date

class MuonThueRepo {
    private val thuVien = ThuVienDataRepo.instance
    private val sachRepo = SachRepo.shared
    private val docGiaRepo = DocGiaRepo.shared
    private var listDemo = mutableListOf<IMuonSachItem>()


    suspend fun search(mKey: String, loaiSearch: Role): List<IMuonSachItem> {
        listDemo = getAllMuonThue()
        val keySearch = mKey.lowercase().trim()
        val filteredList = listDemo.filter { item ->
            when {
                keySearch.isNotBlank() && loaiSearch == Role.DangThue && item.tenDocGia.lowercase()
                    .contains(keySearch) -> item.tinhTrangThue.contains("Đang Thuê")

                keySearch.isBlank() && loaiSearch == Role.DangThue -> item.tinhTrangThue.contains("Đang Thuê")
                keySearch.isNotBlank() && loaiSearch == Role.HetHan && item.tenDocGia.lowercase()
                    .contains(keySearch) -> item.tinhTrangThue.contains("Hết Hạn")

                else -> keySearch.isBlank() && loaiSearch == Role.HetHan && item.tinhTrangThue.contains(
                    "Hết Hạn"
                )
            }
        }
        return filteredList
    }

    private suspend fun getAllMuonThue(): MutableList<IMuonSachItem> {
        val list = thuVien.getAllMuonSach()
        val filteredList = mutableListOf<IMuonSachItem>()

        for (item in list) {
            val docGia = thuVien.getDocGiaByCmnd(item.cmndDocGia) ?: continue
            val hanMuon = docGia.ngayHetHan.dateFromString() ?: continue
            val listSach = item.danhSachMuon
            val itemList = createItemListThongTinThue(docGia, hanMuon,listSach)
            filteredList.add(itemList)
        }

        return filteredList
    }

    private fun createItemListThongTinThue(
        docGia: DocGiaDTO,
        hanMuon: Date,
        danhSachMuon: List<ThongTinThue>
    ): IMuonSachItem {
        val boolean = hanMuon > getDateNow()
        return if (boolean) {
            creatItemList(docGia, danhSachMuon, "Đang Thuê")
        } else {
            creatItemList(docGia, danhSachMuon, "Hết Hạn")
        }
    }

    private fun creatItemList(
        docGia: DocGiaDTO,
        danhSachMuon: List<ThongTinThue>,
        status: String
    ): IMuonSachItem {
        return object : IMuonSachItem {
            override val maDocGia: String = docGia.cmnd
            override val tenDocGia: String = docGia.tenDocGia
            override val tinhTrangThue: String = status
            override val tongLoaiSach: String = danhSachMuon.size.toString()
            override val soLuongThue: String = docGia.soLuongMuon
        }
    }

    suspend fun checkMuonThue(cmnd: String): Boolean {
        return thuVien.checkDocGiaMuon(cmnd)
    }

    suspend fun save(value: IMuonSachSet) {
        val list = value.list.map { createThongTinThue(it) }
        val sublist = list.subList(0,list.size-1)
        val newMuonThue = MuonThue(value.maDocGia.toString(),sublist)
        val boolean = thuVien.addMuonThue(newMuonThue)
        if (boolean) {
            sachRepo.updateSoLuongThueCuaSach(sublist)
            docGiaRepo.updateSoLuongThueCuaDocGia(
                value.maDocGia.toString(),
                sublist.sumOf { it.soLuongMuon })
        }

    }

    private fun createThongTinThue(it: ThongTinSachThueSet): ThongTinThue {
        return ThongTinThue(it.maSach.toString(), it.soLuong.toString().toInt())
    }

    suspend fun del(cmnd: String): Boolean {
        return thuVien.delMuonThuByCmnd(cmnd)
    }


    companion object {
        val shared = MuonThueRepo()
    }
}