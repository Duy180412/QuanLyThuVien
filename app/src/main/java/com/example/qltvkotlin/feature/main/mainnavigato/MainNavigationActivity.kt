package com.example.qltvkotlin.feature.main.mainnavigato

import android.os.Bundle
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseActivity
import com.example.qltvkotlin.feature.helper.Arguments
import com.example.qltvkotlin.feature.helper.StackNavigator
import com.example.qltvkotlin.feature.helper.StackNavigatorOwner
import com.example.qltvkotlin.feature.presentation.router.FragmentRouting
import com.example.qltvkotlin.feature.presentation.router.Routing

class MainNavigationActivity : BaseActivity(R.layout.activity_navigation), StackNavigatorOwner {
    override lateinit var stackNavigator: StackNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stackNavigator = StackNavigator(supportFragmentManager, R.id.container)
        val arguments = Arguments.getArgumentsFrom(intent?.extras) ?: return
        if (arguments is Routing && arguments is FragmentRouting && savedInstanceState == null) {
            stackNavigator.navigateTo(arguments.clazzFragment, arguments.toBundle())
        }
    }


}





