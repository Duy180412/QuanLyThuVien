package com.example.qltvkotlin.domain.model

interface IMuonSach
interface IMuonSachItem : IMuonSach {
    val maDocGia: String
    val tenDocGia: String
    val tinhTrangThue: String
    val tongLoaiSach: String
    val soLuongThue: String
}

interface IThongTinSachThueGet : IThongTinSachThueGeneral {
    override val maSach: String
    override val tenSach: String
    override val soLuong: String
}

interface IThongTinSachThueGeneral {
    val maSach: CharSequence
    val tenSach: CharSequence
    val soLuong: CharSequence
}

open class ThongTinSachThueSet : IThongTinSachThueGeneral {
    override val maSach: Chars = Chars("")
    override val tenSach: Chars = Chars("")
    override val soLuong: Ints = Ints("0")
}

interface IMuonSachSet : IMuonSachGeneral {
    override var maDocGia: Chars
    override val tenDocGia: Chars
    override var list: MutableList<ThongTinSachThueSet>
}

interface IMuonSachGet : IMuonSachGeneral {
    override val maDocGia: String
        get() = ""
    override val tenDocGia: String
        get() = ""
    override val list: List<IThongTinSachThueGet>
        get() = emptyList()
}

interface IMuonSachGeneral : IMuonSach {
    val maDocGia: CharSequence
    val tenDocGia:CharSequence
    val list: List<IThongTinSachThueGeneral>
}

interface IMuonSachBackup : IMuonSach {
    val backUp: IMuonSachGet
}