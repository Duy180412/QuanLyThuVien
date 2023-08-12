package com.example.qltvkotlin.domain.repo

import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.MuonThue
import com.example.qltvkotlin.data.model.ThongTinThue
import com.example.qltvkotlin.data.datasource.roomdata.ThuVienDataRepo
import com.example.qltvkotlin.domain.enumeration.Role
import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.domain.model.getDateNow
import com.example.qltvkotlin.presentation.extension.dateFromString
import com.example.qltvkotlin.presentation.helper.AppStringResources

class MuonSachRepo {
    private val thuVien = ThuVienDataRepo.instance
    private val docGiaRepo = DocGiaRepo.shared
    private val appStringResources: AppStringResources = AppStringResources.shared
    private var backup: MuonThue? = null

    companion object {
        val shared = MuonSachRepo()
    }

    suspend fun searchDangThue(keySearch: String): List<IMuonSachItem> {
        val key = keySearch.lowercase().trim()
        val list =  getAllMuonSachItem(StringId.ConHan).run {
            if (keySearch.isNotBlank()) filter {
                it.tenDocGia.lowercase().contains(key)
            } else this
        }
        return list
    }


    private suspend fun getAllMuonSachItem(role: StringId): List<IMuonSachItem> {
        val list = thuVien.getAllMuonSach().mapNotNull { item ->
            val docGia = docGiaRepo.getInfoFullDocGiaDto(item.cmndDocGia) ?: return@mapNotNull null
            val trangThai = checkHanMuon(docGia.ngayHetHan)
            if (trangThai == role) {
                createItemList(docGia, item.danhSachMuon, trangThai)
            } else {
                null
            }
        }
        return list
    }

    private fun checkHanMuon(ngayHetHan: String): StringId {
        val date = ngayHetHan.dateFromString()
        return if (date != null && date > getDateNow()) return StringId.ConHan
        else StringId.HetHan
    }

    private fun createItemList(
        docGia: DocGiaDTO,
        danhSachMuon: List<ThongTinThue>,
        status: StringId
    ): IMuonSachItem {
        return object : IMuonSachItem {
            override val maDocGia: String = docGia.cmnd
            override val tenDocGia: String = docGia.tenDocGia
            override val tinhTrangThue: String = appStringResources(status)
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

    suspend fun searchHetHan(keySearch: String): List<IMuonSachItem> {
        val key = keySearch.lowercase().trim()
        return getAllMuonSachItem(StringId.HetHan).run {
            if (keySearch.isNotBlank()) filter {
                it.tenDocGia.lowercase().contains(key)
            } else this
        }

    }


}