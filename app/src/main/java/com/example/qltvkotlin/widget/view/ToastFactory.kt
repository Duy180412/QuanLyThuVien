package com.example.qltvkotlin.widget.view

import android.app.Activity
import android.content.Context
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
    fun invoke(text: String) {
        return Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}
