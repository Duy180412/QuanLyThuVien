package com.example.qltvkotlin.presentation.app

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.presentation.action.TakePhotoActionOwner
import com.example.qltvkotlin.presentation.extension.getActivtyBase
import com.example.qltvkotlin.presentation.feature.mainnavigato.MainNavigationActivity
import com.example.qltvkotlin.presentation.helper.OnBackClick

abstract class BaseFragmentNavigation(contentLayoutId: Int) : BaseFragment(contentLayoutId),
    TakePhotoActionOwner {
    override val mActivity by getActivtyBase<MainNavigationActivity>()
    private lateinit var onBackClick: OnBackClick

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackClick = OnBackClick(this.mActivity)
        onBackClickCustom()
        mActivity.actionBarView.onClickEditAndSave = { it -> editOrSaveCase(it) }
    }

    abstract fun editOrSaveCase(it: View)

    private fun onBackClickCustom() {
        onBackClick.checkValueWhenClickBack(
            funCheck = getCheck(),
            funRun = getRun()
        )
        mActivity.actionBarView.onClickBack = { onBackClick.handleOnBackPressed() }
        mActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackClick)
    }

    private fun getRun(): () -> Unit {
        return { dialog.selectYesNo("Hủy Thêm", { this.mActivity.finish() }, {}) }
    }

    abstract fun getCheck(): () -> Boolean

}