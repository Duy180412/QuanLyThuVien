package com.example.qltvkotlin.data.datasource.roomdata


import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.MuonThue
import com.example.qltvkotlin.data.model.SachDTO


class ThuVienDataRepo {
    private val thuVienData = AppDataBase.getInstance()

    suspend fun getAllBook(): List<SachDTO> {
        return thuVienData.getAllSach()
    }

    suspend fun getAllDocGia(): List<DocGiaDTO> {
        return thuVienData.getAllDocGia()
    }

    suspend fun addSach(sachDto: SachDTO): Boolean {
        val addResult = thuVienData.addSach(sachDto)
        return addResult > 0
    }

    suspend fun updateBook(sachDto: SachDTO): Boolean {
        val result = thuVienData.updateSach(sachDto)
        return result > 0
    }

    suspend fun delSachById(id: String): Boolean {
        val result = thuVienData.deleteSach(id)
        return result > 0
    }

    suspend fun checkSachExist(maCheck: String): Boolean {
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

    suspend fun updateDocGia(docGia: DocGiaDTO):Boolean {
        val result = thuVienData.updateDocGia(docGia)
        return result > 0
    }

    suspend fun checkDocGiaMuonExistByCmnd(cmnd: String): Boolean {
        return thuVienData.checkDocGiaMuonExist(cmnd)
    }

    suspend fun addMuonSach(newMuonThue: MuonThue): Boolean {
        val addResult = thuVienData.addMuonThue(newMuonThue)
        return addResult > 0
    }

    suspend fun getAllMuonSach(): List<MuonThue>{
       return thuVienData.getAllMuonSach()
    }

    suspend fun delMuonSachByCmnr(cmnd: String): Boolean {
        val result = thuVienData.deleteMuonSach(cmnd)
        return result > 0
    }

    suspend fun getMuonSachByCmnd(cmnd: String): MuonThue? {
        return  thuVienData.getMuonThueByCmnd(cmnd)
    }



    companion object {
        val instance = ThuVienDataRepo()
    }
}