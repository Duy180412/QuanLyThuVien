package com.example.qltvkotlin.feature.helper.spinner

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatEditText
import com.example.qltvkotlin.R

class EditTextCusTom(context: Context, attrs: AttributeSet) :
    AppCompatEditText(context, attrs) {
    init {
        id = R.id.editseach
        hint = "Search"
        isFocusableInTouchMode = false
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            resources.getDimensionPixelSize(R.dimen.edit_text_height)
        )
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
        setLayoutParams(layoutParams)
    }
}