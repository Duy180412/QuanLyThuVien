package com.example.qltvkotlin.feature.actionbar

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.TopbarViewBinding
import com.example.qltvkotlin.feature.main.sach.AddSachFragment
import com.example.qltvkotlin.feature.main.sach.InfoSachFragment
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.extension.pairLookupOf
import kotlin.reflect.KClass

class ActionBarViewState(
    clazz: KClass<out Fragment>,
    actionBarExt: ActionBarExt
) {
    lateinit var onClickBack: () -> Unit

    private var actionBarSate: ActionBarSate
    private val routing = pairLookupOf<KClass<out Fragment>, ActionBarSate>(
        AddSachFragment::class to ActionBarSate(R.string.title_them_sach, true),
        InfoSachFragment::class to ActionBarSate(R.string.title_them_sach)

    )

    init {
        actionBarSate = routing.requireValueOf(clazz)
        val tieuDe = ActionBarTitileView()
        actionBarExt.setState(tieuDe)
    }


    private inner class ActionBarTitileView : State {
        private var iTem = actionBarSate.iconShow
        private val title = actionBarSate.title
        override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
            TopbarViewBinding.inflate(inflater, parent, false).apply {
                this.titleTop.setText(title)
                this.btnEdit.isSelected = iTem
                this.btnBack.onClick { onClickBack() }
                return this
            }
        }

    }

}

class ActionBarSate(val title: Int, val iconShow: Boolean = false)