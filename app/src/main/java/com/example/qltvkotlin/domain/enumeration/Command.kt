package com.example.qltvkotlin.domain.enumeration

import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.model.IPhotoField
import com.example.qltvkotlin.presentation.widget.fields.SelectTextFeild

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
class AddFeildThemSachDangKi() :Command
class SelectSachMuon(val item: IFieldsCustom) : Command