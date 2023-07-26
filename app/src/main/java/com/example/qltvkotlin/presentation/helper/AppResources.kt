package com.example.qltvkotlin.presentation.helper

import android.text.InputType
import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.enumeration.StringId

class AppResources {
    operator fun get(id: StringId): TextInputCusTomFields {
        return when (id) {
            StringId.MaSach -> TextInputCusTomFields(
                R.string.hintfeilds_masach,
                10,
                hasListener = true
            )
            StringId.TenSach -> TextInputCusTomFields(
                R.string.hintfeilds_tensach,
                hasListener = true
            )
            StringId.LoaiSach -> TextInputCusTomFields(R.string.hintfeilds_loai)
            StringId.TenTacGia -> TextInputCusTomFields(R.string.hintfeilds_tentacgia)
            StringId.NamXuatBan -> TextInputCusTomFields(
                R.string.hintfeilds_namxuatban,
                4,
                InputType.TYPE_CLASS_NUMBER
            )

            StringId.NhaXuatBan -> TextInputCusTomFields(R.string.hintfeilds_nhaxuatban)
            StringId.TongSach -> TextInputCusTomFields(
                R.string.hintfeilds_tongsach,
                3,
                InputType.TYPE_CLASS_NUMBER
            )
            StringId.ConLaiSach -> TextInputCusTomFields(
                R.string.hintfeilds_conlai,
                3,
                InputType.TYPE_CLASS_NUMBER
            )
            StringId.CMND -> TextInputCusTomFields(
                R.string.hintfeilds_cmnd,
                10,
                hasListener = true
            )
            StringId.TenDocGia -> TextInputCusTomFields(
                R.string.hintfeilds_tendocgia,
                hasListener = true
            )
            StringId.SDT -> TextInputCusTomFields(
                R.string.hintfeilds_sdt,11,
                InputType.TYPE_CLASS_PHONE,
                hasListener = true
            )
            StringId.NgayHetHan -> TextInputCusTomFields(
                R.string.hintfeilds_ngayhethan,
                hasListener = true,
                enabled = false
            )
            StringId.SoLuongMuon -> TextInputCusTomFields(
                R.string.hintfeilds_soluongmuon,
            )
            StringId.DocGiaMuon -> TextInputCusTomFields( R.string.hintfeilds_chondocgia, enabled = false)
            StringId.None -> TextInputCusTomFields( R.string.hintfeilds_chondocgia, enabled = false)
        }
    }

    companion object {
        val shared: AppResources = AppResources()
    }
}