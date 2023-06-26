package com.example.qltvkotlin.feature.helper


import android.app.Activity
import androidx.activity.OnBackPressedCallback



class OnBackClick(val context: Activity) : OnBackPressedCallback(true) {
    private lateinit var funcCheck: () -> Boolean
    private lateinit var funcRun: () -> Unit

    fun checkValueWhenClickBack(funCheck: () -> Boolean, funRun: () -> Unit) {
        this.funcCheck = funCheck
        this.funcRun = funRun
    }

    override fun handleOnBackPressed() {
        val boolean = funcCheck()
        if (boolean) {
            funcRun.invoke()
        } else context.finish()
    }

}