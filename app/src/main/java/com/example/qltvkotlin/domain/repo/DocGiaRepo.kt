package com.example.qltvkotlin.domain.repo

import com.example.qltvkotlin.data.datasource.roomdata.ThuVienDataRepo
import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.domain.enumeration.Role
import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.IItemSpinner
import com.example.qltvkotlin.domain.model.getDateNow
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.createImagesFromUrl
import com.example.qltvkotlin.presentation.extension.dateFromString


class DocGiaRepo {
    private val thuVien = ThuVienDataRepo.instance
    private val imagesRepo = ImagesRepo.imagesRepo
    private var backUp: DocGiaDTO? = null

    companion object {
        val shared = DocGiaRepo()
    }


    suspend fun addDocGia(docGiaDTO: DocGiaDTO) {
        thuVien.addDocGia(docGiaDTO)
    }

    private fun creatDocGiaItem(it: DocGiaDTO): IDocGiaItem {
        return object : IDocGiaItem {
            override val cmnd = it.cmnd
            override val photoField = it.avatar.createImagesFromUrl()
            override val tenDocGia = it.tenDocGia
            override val sdt = it.sdt
            override val ngayHetHan = it.ngayHetHan
            override val soLuongMuon = it.soLuongMuon
        }
    }

    suspend fun searchDocGia(mKey: String): List<IDocGiaItem> {
        val keySearch = mKey.lowercase().trim()
        val list =
            thuVien.getAllDocGia().run {
                if (keySearch.isNotBlank()) filter {
                    it.tenDocGia.lowercase().contains(keySearch)
                } else this
            }.map {
                creatDocGiaItem(getInfoFullDocGiaDto(it.cmnd)!!)
            }
        return list
    }

    suspend fun getInfoFullDocGiaDto(cmnd: String): DocGiaDTO? {
        val docGia = thuVien.getDocGiaByCmnd(cmnd) ?: return null
        val muonSach = thuVien.getMuonSachByCmnd(cmnd)
        muonSach?.let {
            docGia.soLuongMuon = muonSach.danhSachMuon.sumOf { it.soLuongMuon }.toString()
        }
        return docGia
    }

    suspend fun delDocGia(cmnd: String): Boolean {
        return getDocGiaById(cmnd)?.let {
            if (thuVien.checkDocGiaMuonExistByCmnd(cmnd)) {
                false
            } else {
                backUp = it
                thuVien.delDocGiaByCmnd(cmnd)
            }
        } ?: return false
    }

    private suspend fun getDocGiaById(id: String): DocGiaDTO? {
        return thuVien.getDocGiaByCmnd(id)
    }

    suspend fun repoDocGia(cmnd: String): Boolean {
        return backUp?.let {
            if (it.cmnd == cmnd) {
                thuVien.addDocGia(it)
            } else false
        } ?: false
    }


    suspend fun checkDocGia(cmnd: String?): Boolean {
        cmnd ?: return true
        return thuVien.checkDocGia(cmnd)
    }


    suspend fun save(editable: HashMap<FieldsId, String>, photo: IImage) {
        val cmnd = editable[FieldsId.CMND] ?: return
        val image = photo.cast<IsImageUri>()?.uriImage
        val urlImg = imagesRepo.saveImage(cmnd, image, Role.DocGia)
        val docGiaDto = createDocGiaDTO(editable, urlImg)
        thuVien.addDocGia(docGiaDto)
    }


    private fun createDocGiaDTO(editable: HashMap<FieldsId, String>, urlImg: String): DocGiaDTO {
        return DocGiaDTO(
            editable[FieldsId.CMND].orEmpty(),
            urlImg,
            editable[FieldsId.TenDocGia].orEmpty(),
            editable[FieldsId.NgayHetHan].orEmpty(),
            editable[FieldsId.SDT].orEmpty(),
            editable[FieldsId.SoLuongMuon].orEmpty(),
        )
    }

    //
//    suspend fun getDocGiaReadOnly(cmnd: String?): IDocGia {
//        cmnd ?: return object : IDocGiaGet {}
//        val docGia = getInfoFullDocGiaDto(cmnd)?: return object : IDocGiaGet {}
//        return createDocGiaOnly(docGia)
//    }
//
//    private fun createDocGiaOnly(docGia: DocGiaDTO): IDocGia {
//        return object : IDocGiaGet {
//            override val cmnd = docGia.cmnd
//            override val photoField = docGia.avatar.createImagesFromUrl()
//            override val tenDocGia = docGia.tenDocGia
//            override val ngayHetHan = docGia.ngayHetHan
//            override val sdt = docGia.sdt
//            override val soLuongMuon = docGia.soLuongMuon
//        }
//    }
//
//    suspend fun update(docGiaEdit: IDocGiaSet) {
//        val urlImg = saveImage(docGiaEdit.cmnd.toString(), docGiaEdit.photoField.getImage())
//        val updateDocGia = createDocGiaDTO(docGiaEdit, urlImg)
//        thuVien.updateDocGia(updateDocGia)
//
//    }
//
//    private fun saveImage(nameImage: String, image: IImage): String {
//        return imagesRepo.saveImage(nameImage, image, Role.DocGia)
//    }
//
    suspend fun getListItemSpinner(mKey: String): List<IItemSpinner> {
        val keySearch = mKey.lowercase().trim()
        val list =
            thuVien.getAllDocGia().run {
                if (keySearch.isNotBlank()) filter {
                    it.tenDocGia.lowercase().contains(keySearch)
                } else this
            }.map {
                creatDocGiaItemSpinner(it)
            }
        return list
    }

    private fun creatDocGiaItemSpinner(it: DocGiaDTO): IItemSpinner {
        return object : IItemSpinner {
            override val key = it.cmnd
            override val nameKey = it.tenDocGia
            override val status = checkThoiGianThe(it.ngayHetHan)
        }
    }

    private fun checkThoiGianThe(ngayHetHan: String): String {
        val date = ngayHetHan.dateFromString()
        date ?: return "Chưa Đăng Kí"
        return if (date > getDateNow()) "Đã Đăng Kí"
        else "Hết Hạn"
    }


}
