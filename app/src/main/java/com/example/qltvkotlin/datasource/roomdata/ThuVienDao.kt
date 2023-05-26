package com.example.qltvkotlin.datasource.roomdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.qltvkotlin.data.model.LoginRequest

@Dao
interface ThuVienDao {

    @Insert
    fun addThuThu(thuThu: LoginRequest)

    @Query("SELECT*From login where idlogin= :id and pass=:pass")
    fun checkExist(id: String, pass: String): Boolean
}