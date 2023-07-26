package com.example.qltvkotlin.presentation.helper

import android.text.InputType
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.presentation.widget.fields.SelectDateFeild
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IHasPropertiesField
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.post
import com.example.qltvkotlin.presentation.widget.fields.PhoneNumberFeild
import com.example.qltvkotlin.presentation.widget.fields.PhotoField
import com.example.qltvkotlin.presentation.widget.fields.SelectTextFeild
import com.example.qltvkotlin.presentation.widget.fields.TextAndNumberFeilds
import com.example.qltvkotlin.presentation.widget.fields.TextFeild
import com.example.qltvkotlin.presentation.widget.fields.ViewFeild

class FetchAddSachCaseFields : FetchCaseFields() {
    override val originalFields = listOfNotNull<IComponent>(
        PhotoField(),
        TextFeild(StringId.MaSach),
        TextFeild(StringId.TenSach),
        TextFeild(StringId.LoaiSach),
        TextFeild(StringId.TenTacGia),
        TextFeild(StringId.NhaXuatBan),
        TextFeild(StringId.NamXuatBan),
        TextFeild(StringId.TongSach)
    )
}
class FetchAddMuonSachCaseFields: FetchCaseFields(){
    override val originalFields = arrayListOf (
        SelectTextFeild(StringId.DocGiaMuon),
        ViewFeild(),
    )

}

class FetchAddDocGiaCaseFields : FetchCaseFields() {
    override val originalFields = listOfNotNull<IComponent>(
        PhotoField(),
        TextFeild(StringId.CMND),
        TextFeild(StringId.TenDocGia),
        PhoneNumberFeild(StringId.SDT),
        SelectDateFeild(StringId.NgayHetHan),
    )

}

abstract class FetchCaseFields {
    val result = MutableLiveData<List<IComponent>>()
    abstract val originalFields: List<IComponent>


    operator fun invoke() {
        result.post(createListFields())
    }

    private fun createListFields(): List<IComponent> {
        return object : MutableList<IComponent> by originalFields.toMutableList(),
            Signal by Signal.MultipleSubscription() {
        }
    }


    fun shouldFetch(): Boolean {
        return result.value.isNullOrEmpty()
    }


}

class TextInputCusTomFields(
    override var hint: Int = 0,
    override var maxEms: Int = -1,
    override var inputType: Int = InputType.TYPE_CLASS_TEXT,
    override var singleLine: Boolean = true,
    override var hasListener: Boolean = false,
    override var enabled: Boolean = true
) : IHasPropertiesField