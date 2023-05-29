package com.example.qltvkotlin.datasource.roomdata
import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.SachDTO
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.domain.model.ISachItem


class ThuVienDataRepo {
    private var listSach: List<SachDTO> = emptyList()
    private var listDocGia: List<DocGiaDTO> = emptyList()

    fun searchSach(mKey: String): List<ISachItem> {
        val keySearch = mKey.lowercase().trim()
        val list = listSach.run {
            if (keySearch.isNotBlank()) filter {
                it.tenSach.lowercase().contains(keySearch)
            } else this
        }.map { creatSachItem(it) }
        return list
    }

    fun searchDocGia(mKey: String): List<IDocGiaItem> {
        val keySearch = mKey.lowercase().trim()
        val list = listDocGia.run {
            if (keySearch.isNotBlank()) filter {
                it.tenDocGia.lowercase().contains(keySearch)
            } else this
        }.map { creatDocGiaItem(it) }
        return list
    }

    private fun creatDocGiaItem(it: DocGiaDTO): IDocGiaItem {
        return object : IDocGiaItem {
            override val cmnd = it.cmnd
            override val tenDocGia = it.tenDocGia
            override val sdt = it.sdt
            override val ngayHetHan = it.ngayHetHan
            override val soLuongMuon = it.soLuongMuon
        }
    }

    private fun creatSachItem(sach: SachDTO): ISachItem {
        return object : ISachItem {
            override val imgSach = sach.imageSach
            override val tenSach = sach.tenSach
            override val tenTacGia = sach.tenTacGia
            override val tong = sach.tongSach
            override val conLai =
                (Integer.parseInt(sach.tongSach) - Integer.parseInt(sach.choThue)).toString()
        }
    }
    companion object{
        val thuVienDataRepo = ThuVienDataRepo()
    }

    init {
        val sach = SachDTO("222","","Đây Là Tên Sách","","","","","12","2")
        val sach2 = SachDTO("223","","Không","","","","","12","2")
        listSach = arrayListOf(sach,sach2)
    }
}