package com.example.qltvkotlin.feature.actionbar

import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.feature.presentation.extension.pairLookupOf

class ActionBarTitleAndSearchSate(itemId: Int, actionBarExt: ActionBarExt) {
    private var actionBarNavigator: ActionBarNavigator
    var search = MutableLiveData<String>()
    lateinit var logout: () -> Unit
    private val routing = pairLookupOf(
        R.string.sach to ActionBarNavigator(R.string.title_sach, R.string.hint_seach_sach),
        R.string.doc_gia to ActionBarNavigator(R.string.title_docgia, R.string.hint_seach_docgia),
        R.string.account to ActionBarNavigator(R.string.account, 0),
        R.string.muon_thue to ActionBarNavigator(R.string.title_sach, R.string.hint_seach_sach)
    )

    init {
        actionBarNavigator = routing.requireValueOf(itemId)
        if (itemId != R.string.account) {
            val tieuDe = ActionBarTitleAndSearchButtonState(actionBarNavigator)
            val timKiem = ActionBarInputSearchState(actionBarNavigator)
            tieuDe.clickSearch = { actionBarExt.setState(timKiem) }
            timKiem.exitSearch = { actionBarExt.setState(tieuDe) }
            timKiem.onSearchListener = { search.postValue(it) }
            actionBarExt.setState(tieuDe)
        } else {
            val account = ActionBarTileAccount(actionBarNavigator)
            account.clickLogout = { logout() }
            actionBarExt.setState(account)
        }
    }
}

class ActionBarNavigator(val title: Int, val hint: Int)
