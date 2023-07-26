package com.example.qltvkotlin.presentation.feature.actionbar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding

interface State {
    fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding
}

class ActionBarExt(
    private val frameLayout: FrameLayout,
) {

    fun setState(state: com.example.qltvkotlin.presentation.feature.actionbar.State) {
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


