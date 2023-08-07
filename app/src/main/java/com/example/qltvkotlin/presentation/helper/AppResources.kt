package com.example.qltvkotlin.presentation.helper

import android.text.InputType
import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.ICharsHint
import com.example.qltvkotlin.domain.model.IDateField
import com.example.qltvkotlin.domain.model.IHasPropertiesField
import com.example.qltvkotlin.domain.model.IHint
import com.example.qltvkotlin.domain.model.INumberField
import com.example.qltvkotlin.domain.model.IPhoneNumberField
import com.example.qltvkotlin.domain.model.ITextInputLayoutField
import com.example.qltvkotlin.domain.model.IIntHint


class AppResources {

    operator fun get(id: FieldsId): IHasPropertiesField {

        return when (id) {
            FieldsId.SoLuongThueCuaSach -> object : CustomFields(), INumberField {
                override var maxEms = 2
                override var iHint: IHint = IntHint(R.string.hintfeilds_slsach)
                override var inputType = InputType.TYPE_CLASS_NUMBER
                override var enabled: Boolean = false
                override var hasListener: Boolean = true
            }

            FieldsId.SachThue ->
                object : CustomFields(), ITextInputLayoutField {
                    override var iHint: IHint = IntHint(R.string.title_them_sach)
                    override var enabled = false
                    override var hasListener: Boolean = true
                }

            FieldsId.MaSach ->
                object : CustomFields(), ITextInputLayoutField {
                    override var iHint: IHint = IntHint(R.string.hintfeilds_masach)
                    override var maxEms: Int = 10
                    override var hasListener: Boolean = true
                }

            FieldsId.TenSach -> object : CustomFields(), ITextInputLayoutField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_tensach)
                override var hasListener: Boolean = true
            }

            FieldsId.LoaiSach -> object : CustomFields(), ITextInputLayoutField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_loai)
            }

            FieldsId.TenTacGia -> object : CustomFields(), ITextInputLayoutField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_tentacgia)
            }

            FieldsId.NamXuatBan -> object : CustomFields(), INumberField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_namxuatban)
                override var maxEms: Int = 4
                override var inputType: Int = InputType.TYPE_CLASS_NUMBER
            }

            FieldsId.NhaXuatBan -> object : CustomFields(), ITextInputLayoutField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_nhaxuatban)
            }

            FieldsId.TongSach -> object : CustomFields(), INumberField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_tongsach)
                override var maxEms: Int = 3
                override var inputType: Int = InputType.TYPE_CLASS_NUMBER
            }

            FieldsId.ConLaiSach -> object : CustomFields(), INumberField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_conlai)
                override var maxEms: Int = 3
                override var inputType: Int = InputType.TYPE_CLASS_NUMBER
            }

            FieldsId.CMND -> object : CustomFields(), ITextInputLayoutField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_cmnd)
                override var maxEms: Int = 10
                override var hasListener: Boolean = true
            }

            FieldsId.TenDocGia -> object : CustomFields(), ITextInputLayoutField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_tendocgia)
                override var hasListener: Boolean = true
            }

            FieldsId.SDT -> object : CustomFields(), IPhoneNumberField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_sdt)
                override var maxEms: Int = 11
                override var inputType: Int = InputType.TYPE_CLASS_NUMBER
                override var hasListener: Boolean = true

            }

            FieldsId.NgayHetHan -> object : CustomFields(), IDateField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_ngayhethan)
                override var hasListener: Boolean = true
                override var enabled: Boolean = false
            }

            FieldsId.SoLuongMuon -> object : CustomFields(), ITextInputLayoutField {
                override var iHint: IHint = IntHint(R.string.hintfeilds_soluongmuon)
                override var maxEms: Int = 2
                override var inputType: Int = InputType.TYPE_CLASS_NUMBER
            }

            FieldsId.DocGiaMuon ->
                object : CustomFields(), ITextInputLayoutField {
                    override var iHint: IHint = IntHint(R.string.hintfeilds_chondocgia)
                    override var enabled: Boolean = false
                    override var hasListener: Boolean = true
                }

        }

    }

    companion object {
        val shared: AppResources = AppResources()
    }
}


abstract class CustomFields : IHasPropertiesField {
    override var iHint: IHint = CharSequenceHint()
    override var maxEms: Int = -1
    override var inputType: Int = InputType.TYPE_CLASS_TEXT
    override var singleLine: Boolean = true
    override var hasListener: Boolean = false
    override var enabled: Boolean = true
}
class IntHint(override val hint: Int = 0) : IIntHint
class CharSequenceHint(override val hint: CharSequence = "") : ICharsHint