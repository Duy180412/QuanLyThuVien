package com.example.qltvkotlin.domain.enumeration

import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.model.IPhotoField

interface Command

interface HasCommandCallback {
    var onCommand: (Command) -> Unit
}

class ThemSachCmd() : Command
class ThemDocGiaCmd() : Command
class XoaSachCmd() : Command
class ThemThemSachRongCmd() : Command

class OnClickItem(val key:String): Command
class OnClickDel(val key:String): Command
class SelecPhoto(val item: IPhotoField):Command
class SelectDocGiaMuonSach(val item: IInputLayoutField): Command
class AddFieldThemSachDangKi() :Command
class SelectSachMuon(val item: IFieldsCustom) : Command
class RemoveField() :Command