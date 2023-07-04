package com.example.qltvkotlin.domain.model

interface IMuonSach
interface IMuonSachItem : IMuonSach {
    val maDocGia: String
    val tenDocGia: String
    val tinhTrangThue: String
    val tongLoaiSach: String
    val soLuongThue: String
}

interface  IThongTinSachThueGet :IThongTinSachThueCreate{
    override val maSach:String
    override val tenSach:String
    override val soLuong:String
}
interface IThongTinSachThueCreate{
    val maSach:CharSequence
    val tenSach:CharSequence
    val soLuong:CharSequence
}
interface IThongTinSachThueSet:IThongTinSachThueCreate{
    override val maSach:Chars
        get() = Chars("")
    override val tenSach: Chars
        get()= Chars("")
    override val soLuong:Ints
        get() = Ints("0")
}
interface IMuonSachSet:IMuonSachCreate{
    override var maDocGia:Chars
    override var list:MutableList<IThongTinSachThueSet>
}
interface IMuonSachGet:IMuonSachCreate{
    override val maDocGia:String
        get() = ""
    override val list: List<IThongTinSachThueGet>
        get() = emptyList()
}
interface IMuonSachCreate : IMuonSach {
    val maDocGia: CharSequence
    val list: List<IThongTinSachThueCreate>
}
interface IMuonSachBackup :IMuonSach{
    val backUp:IMuonSachGet
}