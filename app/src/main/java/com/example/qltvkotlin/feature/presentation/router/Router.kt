package com.example.qltvkotlin.feature.presentation.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment

object Router {
    fun open(ower: ActivityResultCaller, routing: Routing) {
        startActivity(ower, routing)
    }

    private fun startActivity(ower: ActivityResultCaller, routing: Routing) {
        when (ower) {
            is Activity -> ower.startActivity(createIntent(ower,routing))
        }
    }
    private fun createIntent(context: Context, routing: Routing): Intent {
        val intent = when (routing) {
            is ActivityRouting -> Intent(context,routing.clazz.java)
            else -> error("Routing invalid")
        }
        return intent
    }
}