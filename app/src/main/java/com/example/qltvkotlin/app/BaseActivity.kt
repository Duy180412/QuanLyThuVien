package com.example.qltvkotlin.app

import androidx.appcompat.app.AppCompatActivity
import com.example.qltvkotlin.feature.actionbar.ActionBarStateOwnr
import com.example.qltvkotlin.feature.helper.StackNavigatorOwner
import com.example.qltvkotlin.widget.view.ToastFactoryOwner

abstract class BaseActivity(contentLayoutid: Int) : AppCompatActivity(contentLayoutid),
    ToastFactoryOwner {
}