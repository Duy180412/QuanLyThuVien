package com.example.qltvkotlin.domain.repo

import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.MuonThue
import com.example.qltvkotlin.data.model.ThongTinThue
import com.example.qltvkotlin.data.datasource.roomdata.ThuVienDataRepo
import com.example.qltvkotlin.domain.enumeration.Role
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.domain.model.getDateNow
import com.example.qltvkotlin.presentation.extension.dateFromString
import java.util.Date

class MuonSachRepo {
    private val thuVien = ThuVienDataRepo.instance
    private val docGiaRepo = DocGiaRepo.shared
    private var backup: MuonThue? = null

    companion object {
        val shared = MuonSachRepo()
    }


    suspend fun search(mKey: String, loaiSearch: Role): List<IMuonSachItem> {
        val key = mKey.lowercase().trim()
        val filteredList = getAllMuonSachItem().filter { item ->
            when {
                loaiSearch == Role.DangThue && (key.isBlank() || item.tenDocGia.lowercase()
                    .contains(key)) -> item.tinhTrangThue.contains("Đang Thuê")

                loaiSearch == Role.HetHan && (key.isBlank() || item.tenDocGia.lowercase()
                    .contains(key)) -> item.tinhTrangThue.contains("Hết Hạn")

                else -> false
            }
        }
        return filteredList
    }

    private suspend fun getAllMuonSachItem(): MutableList<IMuonSachItem> {
        val list = thuVien.getAllMuonSach()
        val filteredList = mutableListOf<IMuonSachItem>()
        for (item in list) {
            val docGia = docGiaRepo.getInfoFullDocGiaDto(item.cmndDocGia) ?: continue
            val hanMuon = docGia.ngayHetHan.dateFromString() ?: continue
            val listSach = item.danhSachMuon
            val itemList = createItemListThongTinMuon(docGia, hanMuon, listSach)
            filteredList.add(itemList)
        }
        return filteredList
    }

    private fun createItemListThongTinMuon(
        docGia: DocGiaDTO,
        hanMuon: Date,
        danhSachMuon: List<ThongTinThue>
    ): IMuonSachItem {
        return if (hanMuon > getDateNow())
            creatItemList(docGia, danhSachMuon, "Đang Thuê")
        else
            creatItemList(docGia, danhSachMuon, "Hết Hạn")
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

    suspend fun isExistsMuonSach(cmnd: String): Boolean {
        return thuVien.checkDocGiaMuonExistByCmnd(cmnd)
    }

    suspend fun save(cmndDocGiaDangKi: String, listSach: List<ThongTinThue>) {
        val docGiaMuonSach = MuonThue(cmndDocGiaDangKi, listSach)
        thuVien.addMuonSach(docGiaMuonSach)
    }

    suspend fun del(cmnd: String): Boolean {
        return thuVien.getMuonSachByCmnd(cmnd)?.let {
            backup = it
            thuVien.delMuonSachByCmnr(cmnd)
        } ?: false
    }

    suspend fun repo(id: String): Boolean {
        return backup?.let {
            if (it.cmndDocGia == id) {
                thuVien.addMuonSach(it)
            } else false
        } ?: false
    }


}