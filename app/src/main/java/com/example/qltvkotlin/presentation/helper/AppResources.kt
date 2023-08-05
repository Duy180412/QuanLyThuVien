package com.example.qltvkotlin.presentation.helper

import android.text.InputType
import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.model.IDateField
import com.example.qltvkotlin.domain.model.IHasPropertiesField
import com.example.qltvkotlin.domain.model.INumberField
import com.example.qltvkotlin.domain.model.IPhoneNumberField
import com.example.qltvkotlin.domain.model.ITextInputLayoutField

class AppResources {
    operator fun get(id: StringId): IHasPropertiesField {

        return when (id) {
            StringId.SoLuongThueCuaSach -> object : CustomFields(), INumberField {
                override var hint = R.string.hintfeilds_slsach
                override var maxEms = 2
                override var inputType = InputType.TYPE_CLASS_NUMBER
                override var hasListener: Boolean = true
            }

            StringId.SachThue ->
                object : CustomFields(), ITextInputLayoutField {
                    override var hint = R.string.title_them_sach
                    override var enabled = false
                    override var hasListener: Boolean = true
                }

            StringId.MaSach ->
                object : CustomFields(), ITextInputLayoutField {
                    override var hint: Int = R.string.hintfeilds_masach
                    override var maxEms: Int = 10
                    override var hasListener: Boolean = true
                }

            StringId.TenSach -> object : CustomFields(), ITextInputLayoutField {
                override var hint: Int = R.string.hintfeilds_tensach
                override var hasListener: Boolean = true
            }

            StringId.LoaiSach -> object : CustomFields(), ITextInputLayoutField {
                override var hint: Int = R.string.hintfeilds_loai
            }

            StringId.TenTacGia -> object : CustomFields(), ITextInputLayoutField {
                override var hint: Int = R.string.hintfeilds_tentacgia
            }

            StringId.NamXuatBan -> object : CustomFields(), INumberField {
                override var hint: Int = R.string.hintfeilds_namxuatban
                override var maxEms: Int = 4
                override var inputType: Int = InputType.TYPE_CLASS_NUMBER
            }

            StringId.NhaXuatBan -> object : CustomFields(), ITextInputLayoutField {
                override var hint: Int = R.string.hintfeilds_nhaxuatban
            }

            StringId.TongSach -> object : CustomFields(), INumberField {
                override var hint: Int = R.string.hintfeilds_tongsach
                override var maxEms: Int = 3
                override var inputType: Int = InputType.TYPE_CLASS_NUMBER
            }

            StringId.ConLaiSach -> object : CustomFields(), INumberField {
                override var hint: Int = R.string.hintfeilds_conlai
                override var maxEms: Int = 3
                override var inputType: Int = InputType.TYPE_CLASS_NUMBER
            }

            StringId.CMND ->  object : CustomFields(), ITextInputLayoutField {
                override var hint: Int = R.string.hintfeilds_cmnd
                override var maxEms: Int = 10
                override var hasListener: Boolean = true
            }

            StringId.TenDocGia -> object : CustomFields(), ITextInputLayoutField {
                override var hint: Int = R.string.hintfeilds_tendocgia
                override var hasListener: Boolean = true
            }

            StringId.SDT ->  object :CustomFields(),IPhoneNumberField{
                override var hint: Int = R.string.hintfeilds_sdt
                override var maxEms: Int = 11
                override var inputType: Int = InputType.TYPE_CLASS_NUMBER
                override var hasListener: Boolean = true

            }

            StringId.NgayHetHan -> object :CustomFields(),IDateField{
                override var hint: Int =  R.string.hintfeilds_ngayhethan
                override var hasListener: Boolean = true
                override var enabled: Boolean = false
            }

            StringId.SoLuongMuon -> object : CustomFields(), ITextInputLayoutField {
                override var hint: Int =  R.string.hintfeilds_soluongmuon
                override var maxEms: Int = 2
                override var inputType: Int = InputType.TYPE_CLASS_NUMBER
            }
            StringId.DocGiaMuon ->
                object : CustomFields(), ITextInputLayoutField {
                    override var hint: Int =  R.string.hintfeilds_chondocgia
                    override var enabled: Boolean = false
                    override var hasListener: Boolean = true
                }
            else -> error("this Id not sp feild")

        }

    }

    companion object {
        val shared: AppResources = AppResources()
    }
}


abstract class CustomFields : IHasPropertiesField {
    override var hint: Int = 0
    override var maxEms: Int = -1
    override var inputType: Int = InputType.TYPE_CLASS_TEXT
    override var singleLine: Boolean = true
    override var hasListener: Boolean = false
    override var enabled: Boolean = true
}