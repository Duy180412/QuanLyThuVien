package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.data.model.ThongTinThue

interface IMuonSach
interface IMuonSachItem : IMuonSach {
    val maDocGia: String
    val tenDocGia: String
    val tinhTrangThue: String
    val tongLoaiSach: String
    val soLuongThue: String
}

interface IMuonSachSet:IMuonSachCreate{
    override var maDocGia:Chars
    override var list:MutableList<ThongTinThue>
}
interface IMuonSachGet:IMuonSachCreate{
    override val maDocGia:String
        get() = ""
    override val list:List<ThongTinThue>
        get() = emptyList()
}
interface IMuonSachCreate : IMuonSach {
    val maDocGia: CharSequence
    val list: List<ThongTinThue>
}
interface IMuonSachBackup :IMuonSach{
    val backUp:IMuonSachGet
}