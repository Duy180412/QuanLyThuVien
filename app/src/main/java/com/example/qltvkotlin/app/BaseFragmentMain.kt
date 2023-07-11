package com.example.qltvkotlin.app

import com.example.qltvkotlin.feature.main.MainActivity

abstract class BaseFragmentMain(contentLayoutId: Int) : BaseFragment(contentLayoutId) {
    override val mActivity by getActivtyBase<MainActivity>()

}