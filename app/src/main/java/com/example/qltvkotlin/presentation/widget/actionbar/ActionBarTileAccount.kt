package com.example.qltvkotlin.presentation.widget.actionbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.databinding.TopbarTitleAccountBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.LogOut
import com.example.qltvkotlin.presentation.extension.onClick

class ActionBarTileAccount(
    actionBarNavigator: ActionBarNavigator
) : State, HasCommandCallback {
    override var onCommand: (Command) -> Unit = {}
    private val title = actionBarNavigator.title
    override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        TopbarTitleAccountBinding.inflate(inflater, parent, false).apply {
            this.tenFragment.setText(title)
            this.btnLogout.onClick {
                onCommand(LogOut())
            }
            return this
        }
    }
}