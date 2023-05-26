package com.example.qltvkotlin.feature.presentation.extension

import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.model.Updatable

fun View.onClick(callback: View.OnClickListener?) {
    val clickTime = 300
    setOnClickListener {
        val lastClick = getTag(R.id.tag_view_click) as? Long ?: 0L
        val now = System.currentTimeMillis()
        if (now - lastClick > clickTime) {
            callback?.onClick(it)
            setTag(R.id.tag_view_click, now)
        }
    }
}
fun EditText.bindTo(function: () -> CharSequence?) {
    addTextChangedListener {
        val field = function() as? Updatable ?: return@addTextChangedListener
        it ?: return@addTextChangedListener
        field.update(it.toString())
    }
}
fun show(view: View, mValue: Boolean) {
    var isShow = if (mValue) View.VISIBLE else View.GONE
    if (view.visibility == isShow) return;
    view.visibility = isShow
}