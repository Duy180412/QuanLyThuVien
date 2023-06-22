package com.example.qltvkotlin.datasource.roomdata


import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.SachDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ThuVienDataRepo {
    private val thuVienData = AppDataBase.getInstance()

    suspend fun getAllBook(): List<SachDTO> {
        return thuVienData.getAllSach()
    }
    suspend fun getAllDocGia(): List<DocGiaDTO> {
        return thuVienData.getAllDocGia()
    }

    suspend fun addBook(sachDto: SachDTO): Boolean {
        val addResult = thuVienData.addSach(sachDto)
        return addResult > 0
    }

    suspend fun updateBook(sachDto: SachDTO): Boolean {
        val result = withContext(Dispatchers.IO) {
            thuVienData.updateSach(sachDto)
        }
        return result > 0
    }

    suspend fun deleteBook(id: String): Boolean {
        val result = thuVienData.deleteSach(id)
        return result > 0
    }

    suspend fun checkSach(maCheck: String): Boolean {
        return thuVienData.checkSachExists(maCheck)
    }

    suspend fun getSachById(maSach: String): SachDTO? {
        return thuVienData.getSachById(maSach)
    }

    suspend fun delDocGiaByCmnd(cmnd: String): Boolean {
        val result = thuVienData.deleteDocGia(cmnd)
        return result > 0
    }

    suspend fun getDocGiaByCmnd(cmnd: String): DocGiaDTO? {
        return thuVienData.getDocGiaByCmnd(cmnd)
    }

   suspend fun addDocGia(docGia: DocGiaDTO): Boolean {
       val addResult = thuVienData.addDocGia(docGia)
       return addResult > 0
    }

   suspend fun checkDocGia(cmnd: String): Boolean {
       return thuVienData.checkDocGiaExists(cmnd)
    }

    companion object {
        val instance = ThuVienDataRepo()
    }
}