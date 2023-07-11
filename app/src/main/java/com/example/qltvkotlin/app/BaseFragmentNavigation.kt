package com.example.qltvkotlin.app

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.feature.action.TakePhotoActionOwner
import com.example.qltvkotlin.feature.helper.OnBackClick
import com.example.qltvkotlin.feature.main.mainnavigato.MainNavigationActivity

abstract class BaseFragmentNavigation(contentLayoutId: Int) : BaseFragment(contentLayoutId),
    TakePhotoActionOwner {
    override val mActivity by getActivtyBase<MainNavigationActivity>()
    private lateinit var onBackClick: OnBackClick

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackClick = OnBackClick(this.mActivity)
        onBackClickCustom()
        this.mActivity.actionBarView.onClickEditAndSave = { it -> clickEditAndSave(it) }
    }

    abstract fun clickEditAndSave(it: View)

    private fun onBackClickCustom() {
        onBackClick.checkValueWhenClickBack(
            funCheck = getCheck(),
            funRun = getRun()
        )
        this.mActivity.actionBarView.onClickBack = { onBackClick.handleOnBackPressed() }
        this.mActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackClick)
    }

    private fun getRun(): () -> Unit {
        return { dialog.selectYesNo("Hủy Thêm", { this.mActivity.finish() }, {}) }
    }

    abstract fun getCheck(): () -> Boolean

}