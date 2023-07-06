package com.example.qltvkotlin.domain.repo

import com.example.qltvkotlin.data.model.SachDTO
import com.example.qltvkotlin.data.model.ThongTinThue
import com.example.qltvkotlin.datasource.roomdata.ThuVienDataRepo
import com.example.qltvkotlin.domain.model.IBookGet
import com.example.qltvkotlin.domain.model.IBookSet
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.ISach
import com.example.qltvkotlin.domain.model.ISachItem
import com.example.qltvkotlin.domain.model.createImagesFromUrl
import com.example.qltvkotlin.feature.helper.Role
import com.example.qltvkotlin.feature.helper.spinner.IItemSpinner
import com.example.qltvkotlin.feature.presentation.extension.checkIntValue


class SachRepo {
    private val thuVien = ThuVienDataRepo.instance
    private val imagesRepo = ImagesRepo.imagesRepo
    private var bookBackUp: SachDTO? = null

    suspend fun searchSach(mKey: String): List<ISachItem> {
        val keySearch = mKey.lowercase().trim()
        val list =
            thuVien.getAllBook().run {
                if (keySearch.isNotBlank()) filter {
                    it.tenSach.lowercase().contains(keySearch)
                } else this
            }.map {
                creatSachItem(it)
            }
        return list
    }

    private fun creatSachItem(sach: SachDTO): ISachItem {
        return object : ISachItem {
            override val maSach = sach.maSach
            override val imgSach = sach.imageSach.createImagesFromUrl()
            override val tenSach = sach.tenSach
            override val tenTacGia = sach.tenTacGia
            override val tong = sach.tongSach
            override val conLai =
                (Integer.parseInt(sach.tongSach) - Integer.parseInt(sach.choThue)).toString()
        }
    }

    suspend fun checkSach(maCheck: String): Boolean {
        return thuVien.checkSach(maCheck)
    }


    suspend fun save(value: IBookSet) {
        val urlImg = saveImage(value.maSach.toString(), value.imageSach.getImage())
        val sachDto = createSachDTO(value, urlImg)
        thuVien.addBook(sachDto)
    }

    private fun saveImage(nameImage: String, iImage: IImage):String {
       return imagesRepo.saveImage(nameImage, iImage, Role.Sach)
    }

    private fun createSachDTO(it: IBookSet, urlImg: String): SachDTO {
        return SachDTO(
            it.maSach.toString(),
            urlImg,
            it.tenSach.toString(),
            it.loaiSach.toString(),
            it.tenTacGia.toString(),
            it.nhaXuatBan.toString(),
            it.namXuatBan.toString(),
            it.tongSach.toString().checkIntValue(),
            it.choThue.toString().checkIntValue()
        )
    }

    suspend fun getBookReadOnly(id: String?): ISach {
        id ?: return object :IBookGet{}
        val sach = getSachById(id)
        return if (sach == null) object :IBookGet{}
        else createBookOnly(sach)
    }

    private suspend fun getSachById(id: String): SachDTO? {
        return thuVien.getSachById(id)
    }

    private fun createBookOnly(sach: SachDTO): IBookGet {
        return object : IBookGet {
            override val maSach = sach.maSach
            override val imageSach = sach.imageSach.createImagesFromUrl()
            override val tenSach = sach.tenSach
            override val loaiSach = sach.loaiSach
            override val tenTacGia = sach.tenTacGia
            override val nhaXuatBan = sach.nhaXuatBan
            override val namXuatBan = sach.namXuatBan
            override val tongSach = sach.tongSach
            override val choThue = sach.choThue
        }
    }

    suspend fun delSach(id: String): Boolean {
        val bookDel = getSachById(id)
        bookDel ?: return false
        bookBackUp = bookDel
        return thuVien.deleteBook(id)
    }

    suspend fun repoSach(it: String): Boolean {
        val book = bookBackUp ?: return false
        if (book.maSach == it) {
            return thuVien.addBook(book)
        }
        return false
    }

    suspend fun updateSach(bookEdit: IBookSet) {
        val urlImg= saveImage(bookEdit.maSach.toString(),bookEdit.imageSach.getImage())
        val updateBook = createSachDTO(bookEdit,urlImg)
        thuVien.updateBook(updateBook)
    }

    suspend fun getListItemSpinner(key: String): List<IItemSpinner>? {
        val keySearch = key.lowercase().trim()
        val list =
            thuVien.getAllBook().run {
                if (keySearch.isNotBlank()) filter {
                    it.tenSach.lowercase().contains(keySearch)
                } else this
            }.map {
                creatSachItemSpinner(it)
            }
        return list
    }
    private fun creatSachItemSpinner(it: SachDTO): IItemSpinner {
        return object : IItemSpinner {
            override val key = it.maSach
            override val nameKey = it.tenSach
            override val status = (it.tongSach.toInt() - it.choThue.toInt()).toString()
        }
    }

    suspend fun updateSoLuongThueCuaSach(list: List<ThongTinThue>) {
        list.forEach {
            val sach = thuVien.getSachById(it.maSach) ?: return@forEach
            sach.choThue = (sach.choThue.toInt() + it.soLuongMuon).toString()
            thuVien.updateBook(sach)
        }
    }


    companion object {
        val shared = SachRepo()
    }
}