package com.example.qltvkotlin.widget.view

import android.app.AlertDialog
import android.content.Context

fun diaLogSelect(
    context: Context,
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

fun diaLogNotification(
    context: Context,
    text: String,
): AlertDialog {
    val diaLog = AlertDialog.Builder(context)
    diaLog.setMessage(text)
    diaLog.setPositiveButton("Ok") { d, _ ->
        d.dismiss()
    }
    return diaLog.show()
}

