package com.example.qltvkotlin.app

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.domain.model.MessageShowOwner
import com.example.qltvkotlin.feature.action.TakePhotoActionOwner
import com.example.qltvkotlin.feature.helper.OnBackClick
import com.example.qltvkotlin.feature.main.mainnavigato.MainNavigationActivity
import com.example.qltvkotlin.feature.presentation.app.AppPermissionOwer
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.widget.view.DialogFactoryOwner
import com.example.qltvkotlin.widget.view.ToastFactoryOwner

abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId),
    AppPermissionOwer,
    DialogFactoryOwner, ToastFactoryOwner, MessageShowOwner {

}


abstract class BaseFragmentNavigation(contentLayoutId: Int) : BaseFragment(contentLayoutId),
    TakePhotoActionOwner {
    lateinit var mActivity: MainNavigationActivity
    private lateinit var onBackClick: OnBackClick
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainNavigationActivity
        onBackClick = OnBackClick(mActivity)
        onBackClickCustom()
        mActivity.actionBarView.onClickEditAndSave = { it -> clickEditAndSave(it) }
    }


    abstract fun clickEditAndSave(it: View)

    private fun onBackClickCustom() {
        onBackClick.checkValueWhenClickBack(
            funCheck = getCheck(),
            funRun = getRun()
        )
        mActivity.actionBarView.onClickBack = { onBackClick.handleOnBackPressed() }
        mActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackClick)
    }

    fun getRun(): () -> Unit {
        return { dialogFactory.selectYesNo("Hủy Thêm", { mActivity.finish() }, {}) }
    }

    abstract fun getCheck(): () -> Boolean

}
