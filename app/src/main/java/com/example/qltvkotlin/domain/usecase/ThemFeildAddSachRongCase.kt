package com.example.qltvkotlin.domain.usecase

import com.example.qltvkotlin.domain.enumeration.StringIds
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.widget.fields.CustomManyFeilds

class ThemFeildAddSachRongCase {
    operator fun invoke(list: List<IComponent>) {
        val mList = list as? MutableList ?: return
        mList.add(mList.size - 1, CustomManyFeilds(StringIds.AddSachMuon))
        mList.cast<Signal>()?.emit()
    }
}