package com.example.qltvkotlin.domain.repo

import com.example.qltvkotlin.data.model.SachDTO
import com.example.qltvkotlin.data.datasource.roomdata.ThuVienDataRepo
import com.example.qltvkotlin.domain.enumeration.Role
import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.ISachItem
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.toIntOrZeroString
import com.example.qltvkotlin.presentation.extension.createImagesFromUrl
import com.example.qltvkotlin.presentation.extension.toIntOrZeroInt
import com.example.qltvkotlin.presentation.widget.IItemSpinner


class SachRepo {
    private val thuVien = ThuVienDataRepo.instance
    private val imagesRepo = ImagesRepo.imagesRepo
    private var backup: SachDTO? = null

    companion object {
        val shared = SachRepo()
    }

    suspend fun searchSach(mKey: String): List<ISachItem> {
        val keySearch = mKey.lowercase().trim()
        val list =
            thuVien.getAllBook().run {
                if (keySearch.isNotBlank()) filter {
                    it.tenSach.lowercase().contains(keySearch)
                } else this
            }.map {
                creatSachItem(getFullInfoSach(it))
            }
        return list
    }

    private suspend fun getFullInfoSach(sachDTO: SachDTO): SachDTO {
        val allMuonSach = thuVien.getAllMuonSach()
        val tong = allMuonSach.flatMap { docGia -> docGia.danhSachMuon }
            .filter { sach -> sach.maSach == sachDTO.maSach }
            .sumOf { sach -> sach.soLuongMuon }
        sachDTO.choThue = tong.toString()
        return sachDTO
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

    suspend fun checkSach(maCheck: String?): Boolean {
        maCheck ?: return true
        return thuVien.checkSachExist(maCheck)
    }


    suspend fun save(editable: HashMap<FieldsId, String>, photo: IImage) {
        val maSach = editable[FieldsId.MaSach] ?: return
        val image = photo.cast<IsImageUri>()?.uriImage
        val urlImg = imagesRepo.saveImage(maSach, image, Role.Sach)
        val sachDto = createSachDTO(editable, urlImg)
        thuVien.addSach(sachDto)
    }
    suspend fun addSach(sachDTO: SachDTO){
        thuVien.addSach(sachDTO)
    }

    private fun createSachDTO(editable: HashMap<FieldsId, String>, urlImg: String): SachDTO {
        return SachDTO(
            editable[FieldsId.MaSach].orEmpty(),
            urlImg,
            editable[FieldsId.TenSach].orEmpty(),
            editable[FieldsId.LoaiSach].orEmpty(),
            editable[FieldsId.TenTacGia].orEmpty(),
            editable[FieldsId.NhaXuatBan].orEmpty(),
            editable[FieldsId.NamXuatBan].orEmpty(),
            editable[FieldsId.TongSach].orEmpty().toIntOrZeroString(),
            editable[FieldsId.ConLaiSach].orEmpty().toIntOrZeroString(),
        )
    }

//    suspend fun getBookReadOnly(id: String?): ISach {
//        id ?: return object : IBookGet {}
//        val sach = getSachById(id)
//        sach ?: return object : IBookGet {}
//        val sachFull = getFullInfoSach(sach)
//        return createBookOnly(sachFull)
//    }

    suspend fun getSachById(id: String): SachDTO? {
        return thuVien.getSachById(id)
    }

//    private fun createBookOnly(sach: SachDTO): IBookGet {
//        return object : IBookGet {
//            override val maSach = sach.maSach
//            override val imageSach = sach.imageSach.createImagesFromUrl()
//            override val tenSach = sach.tenSach
//            override val loaiSach = sach.loaiSach
//            override val tenTacGia = sach.tenTacGia
//            override val nhaXuatBan = sach.nhaXuatBan
//            override val namXuatBan = sach.namXuatBan
//            override val tongSach = sach.tongSach
//            override val choThue = sach.choThue
//        }
//    }

    suspend fun delSach(id: String): Boolean {
        return getSachById(id)?.let {
            if (checkSachDaChoMuonExistById(id)) {
                false
            } else {
                backup = it
                thuVien.delSachById(id)
            }
        } ?: return false
    }

    private suspend fun checkSachDaChoMuonExistById(id: String): Boolean {
        val allMuonSach = thuVien.getAllMuonSach()
        return allMuonSach.flatMap { docGia -> docGia.danhSachMuon }
            .any { sach -> sach.maSach == id }
    }

    suspend fun repoSach(id: String): Boolean {
        return backup?.let {
            if (it.maSach == id) {
                thuVien.addSach(it)
            } else false
        } ?: false
    }

//    suspend fun updateSach(bookEdit: IBookSet) {
//        val urlImg = saveImage(bookEdit.maSach.toString(), bookEdit.imageSach.getImage())
//        val updateBook = createSachDTO(bookEdit, urlImg)
//        thuVien.updateBook(updateBook)
//    }

    suspend fun getListItemSpinner(key: String): List<IItemSpinner> {
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


}


