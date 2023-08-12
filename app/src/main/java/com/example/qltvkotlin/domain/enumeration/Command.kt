package com.example.qltvkotlin.domain.enumeration

import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.SachDTO
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.domain.model.IPhotoField
import com.example.qltvkotlin.domain.model.ISachItem

interface Command

interface HasCommandCallback {
    var onCommand: (Command) -> Unit
}

class ThemSachCmd() : Command
class ThemDocGiaCmd() : Command
class XoaSachCmd() : Command
class ThemThemSachRongCmd() : Command

class OpenInfoSach(val item: ISachItem) : Command
class XoaSach(val item: ISachItem) : Command
class XoaDocGia(val item: IDocGiaItem) : Command

class SelecPhoto(val item: IPhotoField) : Command
class SelectDocGiaMuonSach(val item: IInputLayoutField) : Command
class AddFieldThemSachDangKi() : Command
class SelectSachMuon(val item: IFieldsCustom) : Command
class RemoveField(val item: IFieldsCustom) : Command
class OnClickSearch() : Command
class LogOut() : Command
class KhoiPhucSach(val sachDto: SachDTO) : Command
class OpenInfoDocGia(val item: IDocGiaItem) : Command
class KhoiPhucDocGia(val docGiaDTO: DocGiaDTO) : Command
class XoaMuonSach(item: IMuonSachItem) : Command
