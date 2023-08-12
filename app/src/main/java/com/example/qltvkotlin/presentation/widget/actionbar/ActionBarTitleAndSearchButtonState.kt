package com.example.qltvkotlin.presentation.widget.actionbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.databinding.TopbarTitleSearchBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.OnClickSearch
import com.example.qltvkotlin.presentation.extension.onClick

class ActionBarTitleAndSearchButtonState(private val title: Int) :
    State {
    lateinit var onSearch: () -> Unit
    override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        TopbarTitleSearchBinding.inflate(inflater, parent, false).apply {
            this.tenFragment.setText(title)
            this.btnSearch.onClick { onSearch() }
            return this
        }
    }
}