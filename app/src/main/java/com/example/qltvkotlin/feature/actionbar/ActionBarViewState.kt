package com.example.qltvkotlin.feature.actionbar

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.TopbarViewBinding
import com.example.qltvkotlin.feature.main.sach.AddSachFragment
import com.example.qltvkotlin.feature.main.sach.TestFragment
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.extension.pairLookupOf
import kotlin.reflect.KClass

class ActionBarViewState(clazz: KClass<out Fragment>, context: Context, actionBarExt: ActionBarExt) {

    private var actionBarView: ActionBarView
    private var contextActivity = context as Activity
    private val routing = pairLookupOf<KClass<out Fragment>, ActionBarView>(
        AddSachFragment::class to ActionBarView(R.string.title_them_sach),
        TestFragment::class to ActionBarView(R.string.title_them_sach)

    )

    init {
        actionBarView = routing.requireValueOf(clazz)
        val tieuDe = ActionBarTitileView()
        tieuDe.clickBack = {contextActivity.finish()}
        actionBarExt.setState(tieuDe)
    }


    private inner class ActionBarTitileView : State {
        lateinit var clickBack: () -> Unit
        private val title = actionBarView.title
        override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
            TopbarViewBinding.inflate(inflater, parent, false).apply {
                this.titleTop.setText(title)
                this.btnBack.onClick { clickBack() }
                return this
            }
        }

    }

}

class ActionBarView(val title: Int)