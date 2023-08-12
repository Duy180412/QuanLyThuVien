package com.example.qltvkotlin.presentation.widget

import android.app.Application
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.helper.ActivityRetriever
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter

class HorizontalLineDecoration(application: Application)  {
    private val divider: Drawable? = ContextCompat.getDrawable(application, R.drawable.divider)
    private val activityRetriever = ActivityRetriever.shared
    companion object {
        lateinit var shared: HorizontalLineDecoration
    }
}