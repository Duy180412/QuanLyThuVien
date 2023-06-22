package com.example.qltvkotlin.domain.model

interface ISach
interface ISachItem {
    val maSach: String
    val imgSach: Images
    val tenSach: String
    val tenTacGia: String
    val tong: String
    val conLai: String
}

interface IBookCreate : ISach {
    val maSach: CharSequence
    val imageSach: Images
    val tenSach: CharSequence
    val loaiSach: CharSequence
    val tenTacGia: CharSequence
    val nhaXuatBan: CharSequence
    val namXuatBan: CharSequence
    val tongSach: CharSequence
    val choThue: CharSequence
}

interface IBookGet : IBookCreate, ISach {
    override val maSach: String
        get() = ""
    override val imageSach: Images
        get() = Images(object : IsImageEmpty {})
    override val tenSach: String
        get() = ""
    override val loaiSach: String
        get() = ""

    override val tenTacGia: String
        get() = ""
    override val nhaXuatBan: String
        get() = ""
    override val namXuatBan: String
        get() = ""
    override val tongSach: String
        get() = ""
    override val choThue: String
        get() = ""
}

interface IBookSet : IBookCreate, ISach {
    override var maSach: Chars
    override var imageSach: Images
    override var tenSach: Chars
    override var loaiSach: Chars
    override var tenTacGia: Chars
    override var nhaXuatBan: Chars
    override var namXuatBan: Chars
    override var tongSach: Chars
    override var choThue: Chars
}

interface IBookBackUp : ISach {
    val bookRead: IBookGet
}


