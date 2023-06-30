package com.example.qltvkotlin.domain.repo

import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.datasource.roomdata.ThuVienDataRepo
import com.example.qltvkotlin.domain.model.IDocGia
import com.example.qltvkotlin.domain.model.IDocGiaGet
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.domain.model.IDocGiaSet
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.createImagesFromUrl
import com.example.qltvkotlin.domain.model.getDateNow
import com.example.qltvkotlin.feature.helper.Role
import com.example.qltvkotlin.feature.helper.spinner.IItemSpinner
import com.example.qltvkotlin.feature.presentation.extension.dateFromString


class DocGiaRepo {
    private val thuVien = ThuVienDataRepo.instance
    private val imagesRepo = ImagesRepo.imagesRepo
    private var backUp: DocGiaDTO? = null


    private fun creatDocGiaItem(it: DocGiaDTO): IDocGiaItem {
        return object : IDocGiaItem {
            override val cmnd = it.cmnd
            override val images = it.avatar.createImagesFromUrl()
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
                creatDocGiaItem(it)
            }
        return list
    }

    suspend fun delDocGia(id: String): Boolean {
        val docGiaDel = getDocGiaById(id)
        docGiaDel ?: return false
        backUp = docGiaDel
        return thuVien.delDocGiaByCmnd(id)
    }

    private suspend fun getDocGiaById(id: String): DocGiaDTO? {
        return thuVien.getDocGiaByCmnd(id)
    }

    suspend fun repoDocGia(cmnd: String): Boolean {
        val docGia = backUp ?: return false
        if (docGia.cmnd == cmnd) {
            return thuVien.addDocGia(docGia)
        }
        return false
    }

    suspend fun checkDocGia(cmnd: String): Boolean {
        return thuVien.checkDocGia(cmnd)
    }

    suspend fun save(value: IDocGiaSet) {
        val image = value.images.getImage()
        val urlImg = imagesRepo.saveImage(value.cmnd.toString(), image, Role.DocGia)
        val docGiaDto = createDocGiaDTO(value, urlImg)
        thuVien.addDocGia(docGiaDto)
    }

    private fun createDocGiaDTO(value: IDocGiaSet, urlImg: String): DocGiaDTO {
        return DocGiaDTO(
            value.cmnd.toString(),
            urlImg,
            value.tenDocGia.toString(),
            value.ngayHetHan.toString(),
            value.sdt.toString(),
            value.soLuongMuon.toString()
        )
    }

    suspend fun getDocGiaReadOnly(id: String?): IDocGia {
        id ?: return object : IDocGiaGet {}
        val docGia = getDocGiaById(id)
        return if (docGia == null) object : IDocGiaGet {}
        else createDocGiaOnly(docGia)
    }

    private fun createDocGiaOnly(docGia: DocGiaDTO): IDocGia {
        return object : IDocGiaGet {
            override val cmnd = docGia.cmnd
            override val images = docGia.avatar.createImagesFromUrl()
            override val tenDocGia = docGia.tenDocGia
            override val ngayHetHan = docGia.ngayHetHan
            override val sdt = docGia.sdt
            override val soLuongMuon = docGia.soLuongMuon
        }
    }

    suspend fun update(docGiaEdit: IDocGiaSet) {
        val urlImg = saveImage(docGiaEdit.cmnd.toString(), docGiaEdit.images.getImage())
        val updateDocGia = createDocGiaDTO(docGiaEdit, urlImg)
        thuVien.updateDocGia(updateDocGia)

    }

    private fun saveImage(nameImage: String, image: IImage): String {
        return imagesRepo.saveImage(nameImage, image, Role.DocGia)
    }

    suspend fun getListItemSpinner(mKey:String): List<IItemSpinner> {
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
        return if(date > getDateNow()) "Đã Đăng Kí"
        else "Hết Hạn"
    }


    companion object {
        val docGiaRepo = DocGiaRepo()
    }
}