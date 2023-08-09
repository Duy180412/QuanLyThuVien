package com.example.qltvkotlin.domain.usecase.docgiadangkythue

import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.presentation.widget.fields.SelectTextField

class HasChangeOnClickBackCase {
    operator fun invoke(list: List<IComponent>): Boolean {
        for (iComponent in list) {
            if (iComponent is SelectTextField) return iComponent.validate()
        }
        return false
    }
}