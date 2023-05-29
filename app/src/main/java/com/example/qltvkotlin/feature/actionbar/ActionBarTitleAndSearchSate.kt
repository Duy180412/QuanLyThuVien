package com.example.qltvkotlin.feature.actionbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.TopbarSearchBinding
import com.example.qltvkotlin.databinding.TopbarTitleSearchBinding
import com.example.qltvkotlin.domain.model.IStringSearch
import com.example.qltvkotlin.domain.model.IsDocGiaSearch
import com.example.qltvkotlin.domain.model.IsSachSearch
import com.example.qltvkotlin.feature.main.docgia.DocGiaFragment
import com.example.qltvkotlin.feature.main.sach.AddSachFragment
import com.example.qltvkotlin.feature.main.sach.SachFragment
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.extension.pairLookupOf
import com.example.qltvkotlin.feature.presentation.extension.show
import kotlin.reflect.KClass

class ActionBarTitleAndSearchSate(clazz: KClass<out Fragment>, val actionBarExt: ActionBarExt) {

    private val routing = pairLookupOf<KClass<out Fragment>, ActionBarState>(
        SachFragment::class to ActionBarNavigator(R.string.title_sach, R.string.hint_seach_sach),
        DocGiaFragment::class to ActionBarNavigator(
            R.string.title_docgia,
            R.string.hint_seach_docgia
        ),
        AddSachFragment::class to ActionBarView(R.string.them_sach)
    )

    init {
        val tieuDe = ActionBarTitleAndSearchButtonState()
        val timKiem = ActionBarInputSearchState()

    }

    private fun getTypeSearch(it: String, clazz: KClass<out Fragment>): IStringSearch? {
        if (clazz == SachFragment::class) return object : IsSachSearch {
            override var mValueSach: String = it

        }
        if (clazz == DocGiaFragment::class) return object : IsDocGiaSearch {
            override var mValueDocGia: String = it
        }
        return null
    }

    inner class ActionBarInputSearchState : State {
        private val hint = actionBarNavigator.hint
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

    inner class ActionBarTitleAndSearchButtonState() : State {
        private val title = actionBarNavigator.title
        lateinit var clickSearch: () -> Unit
        override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
            TopbarTitleSearchBinding.inflate(inflater, parent, false).apply {
                this.tenFragment.setText(title)
                this.btnSearch.onClick { clickSearch() }
                return this
            }
        }
    }
}

class ActionBarNavigator(val title: Int, val hint: Int) : IsActionBarNavigator
class ActionBarView(val title: Int) : IsActionBarView