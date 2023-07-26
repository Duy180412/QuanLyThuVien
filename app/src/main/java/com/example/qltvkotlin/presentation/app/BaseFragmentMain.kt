package com.example.qltvkotlin.presentation.app

import com.example.qltvkotlin.presentation.extension.getActivtyBase
import com.example.qltvkotlin.presentation.feature.mainnavigato.MainActivity

abstract class BaseFragmentMain(contentLayoutId: Int) : BaseFragment(contentLayoutId) {
    override val mActivity by getActivtyBase<MainActivity>()
}