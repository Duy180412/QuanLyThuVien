package com.example.qltvkotlin.feature.actionbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.TopbarSearchBinding
import com.example.qltvkotlin.databinding.TopbarTitleAccountBinding
import com.example.qltvkotlin.databinding.TopbarTitleSearchBinding
import com.example.qltvkotlin.domain.model.IStringSearch
import com.example.qltvkotlin.domain.model.IsDocGiaSearch
import com.example.qltvkotlin.domain.model.IsSachSearch
import com.example.qltvkotlin.feature.main.docgia.DocGiaFragment
import com.example.qltvkotlin.feature.main.sach.SachFragment
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.extension.pairLookupOf
import com.example.qltvkotlin.feature.presentation.extension.show
import kotlin.reflect.KClass

class ActionBarTitleAndSearchSate(itemId: Int, actionBarExt: ActionBarExt) {
    private var actionBarNavigator: ActionBarNavigator
    var search = MutableLiveData<IStringSearch>()
    private val routing = pairLookupOf(
        R.string.sach to ActionBarNavigator(R.string.title_sach, R.string.hint_seach_sach),
        R.string.doc_gia to ActionBarNavigator(R.string.title_docgia, R.string.hint_seach_docgia),
        R.string.account to ActionBarNavigator(R.string.account, 0),
        R.string.muon_thue to ActionBarNavigator(R.string.title_sach, R.string.hint_seach_sach)
    )

    init {
        actionBarNavigator = routing.requireValueOf(itemId)
        if (itemId != R.string.account) {
            val tieuDe = ActionBarTitleAndSearchButtonState()
            val timKiem = ActionBarInputSearchState()
            tieuDe.clickSearch = { actionBarExt.setState(timKiem) }
            timKiem.exitSearch = { actionBarExt.setState(tieuDe) }
            timKiem.onSearchListener = {search.postValue(getTypeSearch(it,itemId))}
            actionBarExt.setState(tieuDe)
        } else{
            val account = ActionBarTileAccount()
            actionBarExt.setState(account)
        }
    }

    private fun getTypeSearch(it: String, clazz:Int): IStringSearch? {
        if (clazz == R.string.sach ) return object : IsSachSearch {
            override var mValueSach: String = it

        }
        if (clazz == R.string.doc_gia) return object : IsDocGiaSearch {
            override var mValueDocGia: String = it
        }
        return null
    }

    private inner class ActionBarInputSearchState : State {
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

    private inner class ActionBarTitleAndSearchButtonState : State {
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

   private inner class ActionBarTileAccount : State {
        private val title = actionBarNavigator.title
        lateinit var clickLogout: () -> Unit
        override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
            TopbarTitleAccountBinding.inflate(inflater, parent, false).apply {
                this.tenFragment.setText(title)
                this.btnLogout.onClick {
                    clickLogout()
                }
                return this
            }
        }
    }
}

class ActionBarNavigator(val title: Int, val hint: Int)
