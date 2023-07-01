package com.example.qltvkotlin.datasource.bo

import com.example.qltvkotlin.data.model.ThongTinThue
import com.example.qltvkotlin.domain.model.Chars
import com.example.qltvkotlin.domain.model.DataTime
import com.example.qltvkotlin.domain.model.HasChange
import com.example.qltvkotlin.domain.model.IBookBackUp
import com.example.qltvkotlin.domain.model.IBookGet
import com.example.qltvkotlin.domain.model.ISach
import com.example.qltvkotlin.domain.model.IBookSet
import com.example.qltvkotlin.domain.model.IDocGia
import com.example.qltvkotlin.domain.model.IDocGiaBackUp
import com.example.qltvkotlin.domain.model.IDocGiaGet
import com.example.qltvkotlin.domain.model.IDocGiaSet
import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.domain.model.IMuonSachBackup
import com.example.qltvkotlin.domain.model.IMuonSachGet
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.domain.model.Images
import com.example.qltvkotlin.domain.model.PhoneNumberChar


class BookEditable(private val iBookGet: IBookGet) : ISach, IBookSet, IBookBackUp,
    HasChange {
    override val bookRead = iBookGet
    override var maSach = Chars(iBookGet.maSach)
    override var imageSach = Images(iBookGet.imageSach)
    override var tenSach = Chars(iBookGet.tenSach)
    override var loaiSach = Chars(iBookGet.loaiSach)
    override var tenTacGia = Chars(iBookGet.tenTacGia)
    override var nhaXuatBan = Chars(iBookGet.nhaXuatBan)
    override var namXuatBan = Chars(iBookGet.namXuatBan)
    override var tongSach = Chars(iBookGet.tongSach)
    override var choThue = Chars(iBookGet.choThue)

    override fun hasChange(): Boolean {
        return iBookGet.maSach != maSach.toString() ||
                iBookGet.tenSach != tenSach.toString() ||
                iBookGet.loaiSach != loaiSach.toString() || iBookGet.tenTacGia != tenTacGia.toString() || iBookGet.nhaXuatBan != nhaXuatBan.toString() || iBookGet.namXuatBan != namXuatBan.toString() || iBookGet.tongSach != tongSach.toString() ||
                this.imageSach.validate()
    }

}


class DocGiaEditable(private val iDocGia: IDocGiaGet) : IDocGia, IDocGiaSet,
    IDocGiaBackUp,
    HasChange {
    override val docGiaRead = iDocGia
    override var cmnd = Chars(iDocGia.cmnd)
    override var images=  Images(iDocGia.images)
    override var tenDocGia = Chars(iDocGia.tenDocGia)
    override var ngayHetHan = DataTime(iDocGia.ngayHetHan)
    override var sdt = PhoneNumberChar(iDocGia.sdt)
    override var soLuongMuon = Chars(iDocGia.soLuongMuon)


    override fun hasChange(): Boolean {
        return iDocGia.cmnd != cmnd.toString() ||
                iDocGia.tenDocGia != tenDocGia.toString() ||
                iDocGia.ngayHetHan != ngayHetHan.toString() ||
                iDocGia.sdt != sdt.toString() || iDocGia.soLuongMuon != soLuongMuon.toString() ||
                this.images.validate()
    }
}
class MuonSachEdittable(iMuonThue: IMuonSachGet):IMuonSach, IMuonSachSet, IMuonSachBackup {
    override val backUp: IMuonSachGet = iMuonThue
    override var maDocGia: Chars = Chars(iMuonThue.maDocGia)
    override var list: MutableList<ThongTinThue> = checkHasValue(iMuonThue.list)

    private fun checkHasValue(list: List<ThongTinThue>): MutableList<ThongTinThue> {
        val mutableList = mutableListOf<ThongTinThue>()
        return if (list.isEmpty()) {
            mutableList.add(ThongTinThue("",0))
            mutableList.add(ThongTinThue("",0))
            mutableList
        }else{
            list.toMutableList()
        }
    }

}