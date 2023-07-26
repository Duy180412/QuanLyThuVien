package com.example.qltvkotlin.domain.usecase

import com.example.qltvkotlin.domain.model.HasChange
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.presentation.extension.cast

class FieldsChangeCase {
    operator fun invoke(list: List<IComponent>?): Boolean {
        list ?: return false
        val isAllValid = list.all {
            val isValid = it.cast<HasChange>()?.hasChange() ?: false
            !isValid
        }
        return !isAllValid
    }
}