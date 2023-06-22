package com.example.qltvkotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "docgia")
data class DocGiaDTO(
    @PrimaryKey val cmnd: String,
    var avatar:String,
    var tenDocGia: String,
    var ngayHetHan: String,
    var sdt: String,
    var soLuongMuon: String

)