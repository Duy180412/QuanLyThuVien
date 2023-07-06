package com.example.qltvkotlin.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.qltvkotlin.datasource.roomdata.Converters

@Entity(tableName = "muonthue")
data class MuonThue(
    @PrimaryKey
    val cmndDocGia: String,
    var danhSachMuon: List<ThongTinThue>
)

@Entity(tableName = "thongtinthuesach")
data class ThongTinThue(
    @PrimaryKey
    val maSach: String,
    var soLuongMuon: Int
)