package com.example.qltvkotlin.feature.helper.spinner

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.R

class RecyclerViewCustom(context: Context, attrs: AttributeSet) :
    RecyclerView(context, attrs) {
    init {
        layoutManager = LinearLayoutManager(context)
        val layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(RelativeLayout.BELOW, R.id.editseach)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        visibility = VISIBLE
        setBackgroundColor(Color.GREEN)
        setLayoutParams(layoutParams)
        addItemDecoration(
            DividerItemDecoration(
                context,
                (layoutManager as LinearLayoutManager).orientation
            )
        )
    }
}
