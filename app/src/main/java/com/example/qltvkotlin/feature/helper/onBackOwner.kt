package com.example.qltvkotlin.feature.helper


import android.app.Activity
import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner


class OnBackClick(val context: Context, val check: Boolean, val function: () -> Unit) :
    OnBackPressedCallback(true) {
    private var activity = context as Activity
    override fun handleOnBackPressed() {
        if (check) function.invoke()
        else activity.finish()
    }
}