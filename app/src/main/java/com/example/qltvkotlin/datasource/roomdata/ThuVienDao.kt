package com.example.qltvkotlin.datasource.roomdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.LoginRequest
import com.example.qltvkotlin.data.model.SachDTO

@Dao
interface ThuVienDao {

    @Insert
    fun addThuThu(thuThu: LoginRequest)

    @Query("SELECT*From login where idlogin= :id and pass=:pass")
    fun checkExist(id: String, pass: String): Boolean

    @Insert
    fun addSach(sach:SachDTO)

    @Query("SELECT * From sach")
    fun getAllSach(): List<SachDTO>

    @Insert
    fun addDocGia(docGia:DocGiaDTO)
    @Query("SELECT * From docgia")
    fun getAllDocGia(): List<DocGiaDTO>

}