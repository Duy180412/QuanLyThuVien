package com.example.qltvkotlin.presentation.helper

import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.presentation.widget.fields.SelectDateField
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.repo.SachRepo
import com.example.qltvkotlin.domain.usecase.toMutableListCustom
import com.example.qltvkotlin.presentation.extension.post
import com.example.qltvkotlin.presentation.widget.fields.PhoneNumberFeild
import com.example.qltvkotlin.presentation.widget.fields.PhotoField
import com.example.qltvkotlin.presentation.widget.fields.SelectTextField
import com.example.qltvkotlin.presentation.widget.fields.TextField
import com.example.qltvkotlin.presentation.widget.fields.ViewFeild

class FetchSachCaseFields(
    private val sachRepo: SachRepo = SachRepo()
) : FetchCaseFields() {
    override val originalFields = listOfNotNull<IComponent>(
        PhotoField(),
        TextField(FieldsId.MaSach),
        TextField(FieldsId.TenSach),
        TextField(FieldsId.LoaiSach),
        TextField(FieldsId.TenTacGia),
        TextField(FieldsId.NhaXuatBan),
        TextField(FieldsId.NamXuatBan),
        TextField(FieldsId.TongSach)
    )

}

class FetchAddMuonSachCaseFields : FetchCaseFields() {
    override val originalFields = listOfNotNull(
        SelectTextField(FieldsId.DocGiaMuon),
        ViewFeild()
    )

}

class FetchDocGiaCaseFields : FetchCaseFields() {

    override val originalFields = listOfNotNull<IComponent>(
        PhotoField(),
        TextField(FieldsId.CMND),
        TextField(FieldsId.TenDocGia),
        PhoneNumberFeild(FieldsId.SDT),
        SelectDateField(FieldsId.NgayHetHan),
    )

}

abstract class FetchCaseFields {
    val result = MutableLiveData<List<IComponent>>()
    abstract val originalFields: List<IComponent>


    operator fun invoke() {
        val instance = originalFields.toMutableList()
        result.post(toMutableListCustom(instance))
    }

    fun shouldFetch(): Boolean {
        return result.value.isNullOrEmpty()
    }


}

