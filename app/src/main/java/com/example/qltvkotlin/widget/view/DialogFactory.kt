package com.example.qltvkotlin.widget.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

interface DialogFactoryOwner {
    val dialogFactory: DialogFactory
        get() {
            return when (this) {
                is Fragment -> DialogFactory(requireContext())
                is Activity -> DialogFactory(this)
                else -> error("Not Support")
            }
        }
}

class DialogFactory(val context: Context) {
    fun selectYesNo(
        text: String,
        onAccept: () -> Unit,
        onCancel: () -> Unit
    ): AlertDialog {
        val diaLog = AlertDialog.Builder(context)
        diaLog.setMessage(text)
        diaLog.setPositiveButton("Yes") { d, _ ->
            onAccept.invoke()
            d.dismiss()
        }
        diaLog.setNegativeButton("No") { d, _ ->
            onCancel.invoke()
            d.dismiss()

        }
        return diaLog.show()
    }

    fun notification(
        text: String,
    ): AlertDialog {
        val diaLog = AlertDialog.Builder(context)
        diaLog.setTitle("Error")
        diaLog.setMessage(text)
        diaLog.setPositiveButton("Ok") { d, _ ->
            d.dismiss()
        }
        return diaLog.show()
    }

    fun bottomUndo(view: View, it: String, function: () -> Unit) {
        val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo") {
            function.invoke()
        }
        return snackbar.show()
    }
}


