package com.example.qltvkotlin.feature.actionbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.databinding.TopbarSearchBinding
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.extension.show

class ActionBarInputSearchState(private val hint: Int) : State {
    lateinit var exitSearch: () -> Unit
    lateinit var onSearchListener: (String) -> Unit
    override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        TopbarSearchBinding.inflate(inflater, parent, false).apply {
            this.editseach.setHint(hint)
            this.editseach.addTextChangedListener {
                onSearchListener(it.toString())
                show(this.clearSearch, it?.length!! > 0)
            }
            this.offSearch.onClick {
                exitSearch()
            }
            this.clearSearch.onClick {
                this.editseach.setText("")
            }
            show(this.clearSearch, this.editseach.length() > 0)
            return this
        }
    }
}