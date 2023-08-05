package com.example.qltvkotlin.presentation.widget

import android.app.Application
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.qltvkotlin.R

class HorizontalLineDecoration(application: Application) : RecyclerView.ItemDecoration() {
    private val divider: Drawable? = ContextCompat.getDrawable(application, R.drawable.divider)

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider!!.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }
    fun init(){

    }
    companion object {
        lateinit var shared: HorizontalLineDecoration
    }
}