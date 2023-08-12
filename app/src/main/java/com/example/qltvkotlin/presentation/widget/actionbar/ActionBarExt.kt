package com.example.qltvkotlin.presentation.widget.actionbar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback

interface State {
    fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding
}

class ActionBarExt(
    private val frameLayout: FrameLayout,
):HasCommandCallback {
    override var onCommand: (Command) -> Unit = {}
    fun setState(state: State) {
        if(state is HasCommandCallback) state.onCommand = {this.onCommand(it)}
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


