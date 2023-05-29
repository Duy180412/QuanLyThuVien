package com.example.qltvkotlin.feature.actionbar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding

interface State {
    fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding
}

interface ActionBarState
interface ActionBarStateOwnr {
    val actionBarExt: ActionBarExt
}

interface IsActionBarNavigator : ActionBarState
interface IsActionBarView : ActionBarState


class ActionBarExt(
    private val frameLayout: FrameLayout,
    private val context: Context
) {

    fun setState(state: State) {
        if (state is AutoCloseable) {
            try {
                state.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        frameLayout.removeAllViews()
        state.onCreate(LayoutInflater.from(frameLayout.context), frameLayout)
            .apply { frameLayout.addView(this.root) }
    }
}


