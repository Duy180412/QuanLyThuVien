package com.example.qltvkotlin.domain.usecase.docgiadangkythue

import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.HasValueKey
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.helper.AppStringResources
import com.example.qltvkotlin.presentation.helper.CharSequenceHint
import com.example.qltvkotlin.presentation.widget.IItemSpinner
import com.example.qltvkotlin.presentation.widget.fields.CustomManyFields

class ThemSachChoDocGiaThueCase(
    private val dialogProvider: DialogProvider = DialogProvider.shared,
    private val stringResources: AppStringResources = AppStringResources.shared
) {
    suspend operator fun invoke(item: IFieldsCustom, component: List<IComponent>) {
        val selectTextFeild = item.getSelectField()
        val numberField = item.getNumberField()
        val sachDuocChon = dialogProvider.chonSach() ?: return
        val isExits = checkExist(sachDuocChon, component)
        if (isExits) throw Exception("Sách Này Đã Được Thêm")
        selectTextFeild.cast<Updatable>()?.update(sachDuocChon.nameKey)
        selectTextFeild.cast<HasValueKey>()?.key = sachDuocChon.key
        numberField.iHint = CharSequenceHint(stringResources(StringId.MaxInt, sachDuocChon.status))
        numberField.setMax(sachDuocChon.status)
        numberField.enabled = true
        numberField.cast<Updatable>()?.update("0")

    }

    private fun checkExist(sachDuocChon: IItemSpinner, list: List<IComponent>): Boolean {
        val listSach = mutableListOf<String>()
        list.forEach {
            when (it) {
                is CustomManyFields -> listSach.add(it.getSelectField().key.toString())
            }
        }
        listSach.forEach {
            if (it == sachDuocChon.key) return true
        }
        return false
    }
}