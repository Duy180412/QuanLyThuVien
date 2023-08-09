package com.example.qltvkotlin.domain.usecase.docgiadangkythue

import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.widget.fields.CustomManyFields

class ThemTruongRongDeNhapSachChoThueCase {
    operator fun invoke(list: List<IComponent>) {
        val mList = list as? MutableList ?: return
        mList.add(mList.size - 1, CustomManyFields())
        mList.cast<Signal>()?.emit()
    }
}