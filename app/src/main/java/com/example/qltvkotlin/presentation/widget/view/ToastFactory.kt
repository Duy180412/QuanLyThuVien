package com.example.qltvkotlin.presentation.widget.view

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

interface ToastFactoryOwner {
    val toast: ToastFactory
        get() {
            return when (this) {
                is Activity -> ToastFactory(this)
                is Fragment -> ToastFactory(requireContext())
                else -> error("Not Support")
            }
        }
}

class ToastFactory(val context: Context)  {
    operator fun invoke(text: String) {
        return Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}
