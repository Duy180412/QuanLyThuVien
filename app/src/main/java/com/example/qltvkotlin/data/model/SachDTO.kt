package com.example.qltvkotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "sach")
data class SachDTO(
    @PrimaryKey val maSach:String,
    var imageSach:String,
    var tenSach: String,
    var loaiSach:String,
    var tenTacGia:String,
    var nhaXuatBan:String,
    var namXuatBan:String,
    var tongSach:String,
    var choThue:String
)