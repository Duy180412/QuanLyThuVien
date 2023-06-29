package com.example.qltvkotlin.feature.main


import android.os.Bundle
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseActivity
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.databinding.ActivityMainBinding
import com.example.qltvkotlin.feature.actionbar.ActionBarExt
import com.example.qltvkotlin.feature.actionbar.ActionBarStateOwnr
import com.example.qltvkotlin.feature.actionbar.ActionBarTitleAndSearchSate
import com.example.qltvkotlin.feature.helper.StackNavigator
import com.example.qltvkotlin.feature.helper.StackNavigatorOwner

class MainActivity : BaseActivity(R.layout.activity_main), StackNavigatorOwner,
    ActionBarStateOwnr {
    override lateinit var actionBarExt: ActionBarExt
    override lateinit var stackNavigator: StackNavigator
    lateinit var actionBarMain: ActionBarTitleAndSearchSate
    private val binding by viewBinding { ActivityMainBinding.bind(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabAdapter = BottomNavigatorMenuMain(binding.containerMainNavigation)
        actionBarExt = ActionBarExt(binding.topbar.containertopbar)
        stackNavigator = StackNavigator(supportFragmentManager, binding.contentview.id)
        stackNavigator.navigateTo(tabAdapter.selectedItemId)
        actionBarMain = ActionBarTitleAndSearchSate(tabAdapter.selectedItemId, actionBarExt)


        tabAdapter.setOnItemSelectedListener { itemId ->
            val idFragmentLast = stackNavigator.getIdLastFragment()
            if (idFragmentLast != itemId) {
                stackNavigator.navigateTo(itemId)
                actionBarMain = ActionBarTitleAndSearchSate(itemId, actionBarExt)
                true
            } else false
        }
    }


}

