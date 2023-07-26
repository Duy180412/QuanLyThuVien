package com.example.qltvkotlin.presentation.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

inline fun <reified T> Any?.cast(): T? {
    if (this is T) return this
    return null
}

fun String.checkIntValue(): String {
    if (this.isEmpty()) return "0"
    try {
        this.toInt()
    } catch (_: Exception) {
        return "0"
    }
    return this
}

fun String.checkStringNull(): String {
    return this.ifEmpty { "Rỗng" }
}

infix fun Boolean.check(run: () -> Unit) {
    if (this) run()
}

fun String.dateFromString():Date?{
    return try {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(this)
    }catch (_:Exception){
        null
    }
}