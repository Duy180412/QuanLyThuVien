package com.example.qltvkotlin.domain.usecase

import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IHasListener
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.ImageEmpty
import com.example.qltvkotlin.domain.model.MessageId
import com.example.qltvkotlin.domain.model.Validable
import com.example.qltvkotlin.domain.repo.SachRepo
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.widget.fields.PhotoField
import com.example.qltvkotlin.presentation.widget.fields.TextFeild

class LuuSachCase {
    private val sachRepo: SachRepo = SachRepo.shared
    suspend operator fun invoke(list: List<IComponent>) {
        var photo: IImage = ImageEmpty
        val editable = hashMapOf<StringId, String>()
        val isAllValid = list.filter { it.cast<IHasListener>()?.hasListener == true }.all {
            val isValid = it.cast<Validable>()?.validate() ?: true
            isValid
        }
        if (!isAllValid) error("Có trường sai")

        list.forEach {
            when (it) {
                is PhotoField -> photo = it.getImage()
                is TextFeild -> editable[it.getFieldsID()] = it.getValue()
            }
        }
        val checkExits = sachRepo.checkSach(editable[StringId.MaSach])
        if (checkExits) throw Error(MessageId.isExist.value)
        sachRepo.save(editable,photo)
    }
}