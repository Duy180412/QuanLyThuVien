package com.example.qltvkotlin.domain.usecase

import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IHasListener
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.ImageEmpty
import com.example.qltvkotlin.domain.model.MessageId
import com.example.qltvkotlin.domain.model.Validable
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.widget.fields.PhotoField
import com.example.qltvkotlin.presentation.widget.fields.TextField

class LuuDocGiaCase {
    private val  docGiaRepo = DocGiaRepo.shared
    suspend operator fun invoke(list: List<IComponent>) {
        var photo: IImage = ImageEmpty
        val editable = hashMapOf<FieldsId, String>()
        val isAllValid = list.filter { it.cast<IHasListener>()?.hasListener == true }.all {
            val isValid = it.cast<Validable>()?.validate() ?: true
            isValid
        }
        if (!isAllValid) error("Có trường sai")

        list.forEach {
            when (it) {
                is PhotoField -> photo = it.getImage()
                is TextField -> editable[it.getFieldsID()] = it.getValue()
            }
        }
        val checkExits = docGiaRepo.checkDocGia(editable[FieldsId.CMND])
        if (checkExits) throw Error(MessageId.isExist.value)
        docGiaRepo.save(editable,photo)
    }
}