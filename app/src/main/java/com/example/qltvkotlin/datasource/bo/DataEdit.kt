package com.example.qltvkotlin.datasource.bo

import com.example.qltvkotlin.domain.model.Chars
import com.example.qltvkotlin.domain.model.HasChange
import com.example.qltvkotlin.domain.model.ISach
import com.example.qltvkotlin.domain.model.ISachEditable
import com.example.qltvkotlin.domain.model.ISachReadOnly
import com.example.qltvkotlin.domain.model.Images


class SachEdit(iSachReadOnly: ISachReadOnly) : ISach, ISachEditable, HasChange {
    override val iSachRead: ISachReadOnly = iSachReadOnly
    override var maSach: Chars = Chars(iSachReadOnly.maSach)
    override var imageSach: Images = iSachReadOnly.imageSach
    override var tenSach: Chars = Chars(iSachReadOnly.tenSach)
    override var loaiSach: Chars = Chars(iSachReadOnly.loaiSach)
    override var tenTacGia: Chars = Chars(iSachReadOnly.tenTacGia)
    override var nhaXuatBan: Chars = Chars(iSachReadOnly.nhaXuatBan)
    override var namXuatBan: Chars = Chars(iSachReadOnly.namXuatBan)
    override var tongSach: Chars = Chars(iSachReadOnly.tongSach)
    override var choThue: Chars = Chars(iSachReadOnly.choThue)

    override fun hasChange(): Boolean {
        return iSachRead.maSach != maSach.toString() ||
                iSachRead.tenSach != tenSach.toString() ||
                iSachRead.loaiSach != loaiSach.toString() ||
                iSachRead.tenTacGia != tenTacGia.toString() ||
                iSachRead.nhaXuatBan != nhaXuatBan.toString() ||
                iSachRead.namXuatBan != namXuatBan.toString() ||
                iSachRead.tongSach != tongSach.toString()||
                this.imageSach.validate()


    }

}