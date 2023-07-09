package com.example.qltvkotlin.feature.main.mainnavigato

import android.os.Bundle
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseActivity
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.databinding.ActivityNavigationBinding
import com.example.qltvkotlin.feature.actionbar.ActionBarExt
import com.example.qltvkotlin.feature.actionbar.ActionBarViewState
import com.example.qltvkotlin.feature.helper.Arguments
import com.example.qltvkotlin.feature.helper.StackNavigator
import com.example.qltvkotlin.feature.presentation.router.FragmentRouting
import com.example.qltvkotlin.feature.presentation.router.Routing

class MainNavigationActivity : BaseActivity(R.layout.activity_navigation) {
    private lateinit var actionBarExt: ActionBarExt
    private lateinit var stackNavigator: StackNavigator
    lateinit var actionBarView: ActionBarViewState
    private val binding by viewBinding { ActivityNavigationBinding.bind(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stackNavigator = StackNavigator(supportFragmentManager, R.id.contentview)
        val arguments = Arguments.getArgumentsFrom(intent?.extras) ?: return
        actionBarExt = ActionBarExt(binding.containertopbar)


        if (arguments is Routing && arguments is FragmentRouting) {
            stackNavigator.navigateTo(arguments.clazzFragment, arguments.toBundle())
            actionBarView = ActionBarViewState(arguments.clazzFragment, actionBarExt)

        }

    }
}





