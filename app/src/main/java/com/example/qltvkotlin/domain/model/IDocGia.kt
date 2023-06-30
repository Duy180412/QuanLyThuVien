package com.example.qltvkotlin.domain.model


interface IDocGia
interface IDocGiaItem {
    val cmnd: String
    val images: Images
    val tenDocGia: String
    val sdt: String
    val ngayHetHan: String
    val soLuongMuon: String
}

interface IDocGiaInfo : IDocGia {
    val cmnd: String
    val tenDocGia: String
    val trangThai: Boolean
}

interface IDocGiaCreate : IDocGia {
    val cmnd: CharSequence
    val images: Images
    val tenDocGia: CharSequence
    val ngayHetHan: CharSequence
    val sdt: CharSequence
    val soLuongMuon: CharSequence
}

interface IDocGiaGet : IDocGia, IDocGiaCreate {
    override val cmnd: String
        get() = ""
    override val images: Images
        get() = Images(object : IsImageEmpty {})
    override val tenDocGia: String
        get() = ""
    override val ngayHetHan: String
        get() = ""
    override val sdt: String
        get() = ""
    override val soLuongMuon: String
        get() = ""
}

interface IDocGiaSet : IDocGiaCreate, IDocGia {
    override var cmnd: Chars
    override var images: Images
    override var tenDocGia: Chars
    override var ngayHetHan: DataTime
    override var sdt: PhoneNumberChar
    override var soLuongMuon: Chars

}

interface IDocGiaBackUp : IDocGia {
    val docGiaRead: IDocGiaGet
}

