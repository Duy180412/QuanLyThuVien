package com.example.qltvkotlin.feature.actionbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.databinding.TopbarViewBinding
import com.example.qltvkotlin.feature.presentation.extension.onClick

class ActionBarViewState(state: ActionBarView) : State {
    val title = state.title
    lateinit var clickBack: () -> Unit

    override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        TopbarViewBinding.inflate(inflater, parent, false).apply {
            this.titleTop.setText(title)
            this.btnBack.onClick { clickBack() }
            return this
        }
    }

}