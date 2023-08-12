package com.example.qltvkotlin.domain.enumeration

enum class Role {
    None,
    Sach,
    DocGia,
    ConHan,
    HetHan

}
enum class TypeSearch{
    Sach,
    DocGia,
    ConHan,
    HetHan
}

enum class FieldsId {
    MaSach,
    TenSach,
    LoaiSach,
    TenTacGia,
    NhaXuatBan,
    NamXuatBan,
    TongSach,
    ConLaiSach,
    CMND,
    TenDocGia,
    SDT,
    NgayHetHan,
    SoLuongMuon,
    DocGiaMuon,
    SachThue,
    SoLuongThueCuaSach

}

enum class StringId {
    MaxInt,
    TenSach,
    TenTacGia,
    TongSach,
    ConLai,
    TenDocGia,
    Sdt,
    HanDangKi,
    ChoThue,
    HetHan,
    ConHan,
    Loai,
    TongMuon

}

enum class SelectPhotoType {
    Camera,
    Gallery
}
