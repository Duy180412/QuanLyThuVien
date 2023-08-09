package com.example.qltvkotlin.data.datasource.roomdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.MuonThue
import com.example.qltvkotlin.data.model.SachDTO

@Dao
interface ThuVienDao {

    @Insert
    suspend fun addSach(sach: SachDTO): Long

    @Query("SELECT * From sach")
    suspend fun getAllSach(): List<SachDTO>

    @Query("DELETE FROM sach WHERE maSach=:maSach")
    suspend fun deleteSach(maSach: String): Int

    @Update
    suspend fun updateSach(sachDto: SachDTO): Int

    @Query("SELECT EXISTS(SELECT 1 FROM sach WHERE maSach = :maSach)")
    suspend fun checkSachExists(maSach: String): Boolean

    @Query("SELECT * FROM sach WHERE maSach = :maSach")
    suspend fun getSachById(maSach: String): SachDTO?

    @Query("SELECT * From docgia")
    suspend fun getAllDocGia(): List<DocGiaDTO>

    @Query("DELETE FROM docgia WHERE cmnd=:cmnd")
    suspend fun deleteDocGia(cmnd: String): Int

    @Query("SELECT * FROM docgia WHERE cmnd = :cmnd")
    suspend fun getDocGiaByCmnd(cmnd: String): DocGiaDTO?

    @Insert
    suspend fun addDocGia(docGia: DocGiaDTO): Long

    @Query("SELECT EXISTS(SELECT 1 FROM docgia WHERE cmnd = :cmnd)")
    suspend fun checkDocGiaExist(cmnd: String): Boolean

    @Update
    suspend fun updateDocGia(docGia: DocGiaDTO): Int

    @Query("SELECT EXISTS(SELECT 1 FROM muonthue WHERE cmndDocGia = :cmnd)")
    suspend fun checkDocGiaMuonExist(cmnd: String): Boolean

    @Insert
    suspend fun addMuonThue(newMuonThue: MuonThue): Long

    @Query("SELECT * From muonthue")
    suspend fun getAllMuonSach(): List<MuonThue>

    @Query("DELETE FROM muonthue WHERE cmndDocGia=:cmnd")
    suspend fun deleteMuonSach(cmnd: String): Int

    @Query("SELECT * FROM muonthue WHERE cmndDocGia = :cmnd")
    suspend fun getMuonThueByCmnd(cmnd: String): MuonThue?


}