package com.example.qltvkotlin.feature.helper.spinner

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.R
import com.example.qltvkotlin.feature.presentation.extension.onClick


class SpinnerCustom(
    context: Context,
    attrs: AttributeSet
) : RelativeLayout(context, attrs) {
    private val editText: EditTextCusTom
    private val recyclerView: RecyclerViewCustom
    private val spinnerPopup: SpinnerPopup
    private val adapter: AdapterSpinnerCustom
    private val list: List<IItemSpinner> = emptyList()


    init {
        editText = EditTextCusTom(context, attrs)
        recyclerView = RecyclerViewCustom(context, attrs)
        adapter = AdapterSpinnerCustom(recyclerView)
        spinnerPopup = SpinnerPopup(context, editText, recyclerView)
        editText.onClick {
            spinnerPopup.show()
        }

        addView(editText)
    }


}

class SpinnerPopup(
    context: Context,
    val editText: EditTextCusTom,
    val reyceView: RecyclerViewCustom
) : PopupWindow(context) {

    init {
        contentView = reyceView
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true
        inputMethodMode =INPUT_METHOD_NOT_NEEDED
        setBackgroundDrawable(ColorDrawable(Color.WHITE))
    }

    fun show() {
        if (!editText.isFocusableInTouchMode && !isShowing) {
            editText.isFocusableInTouchMode = !editText.isFocusableInTouchMode
            showSearch(!isShowing)
        } else if (editText.isFocusableInTouchMode && !isShowing) {
            showSearch(!isShowing)
        }
    }

    private fun showSearch(shouldShow: Boolean) {

        if (shouldShow) {
            if (!isShowing) {
                val location = IntArray(2)
                editText.getLocationOnScreen(location)
                val x = location[0]
                val y = location[1] + editText.height
                showAtLocation(editText, Gravity.NO_GRAVITY, x, y)
            }
        } else {
            dismiss()
        }
    }

}


interface IItemSpinner {
    val key: String
    val nameKey: String
    val status: String
}