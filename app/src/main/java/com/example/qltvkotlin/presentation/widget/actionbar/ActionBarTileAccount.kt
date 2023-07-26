package com.example.qltvkotlin.presentation.widget.actionbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.databinding.TopbarTitleAccountBinding
import com.example.qltvkotlin.presentation.feature.actionbar.State
import com.example.qltvkotlin.presentation.extension.onClick

class ActionBarTileAccount(actionBarNavigator: ActionBarNavigator) :
    State {
    private val title = actionBarNavigator.title
    lateinit var clickLogout: () -> Unit
    override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        TopbarTitleAccountBinding.inflate(inflater, parent, false).apply {
            this.tenFragment.setText(title)
            this.btnLogout.onClick {
                clickLogout()
            }
            return this
        }
    }
}