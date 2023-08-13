package com.example.qltvkotlin.presentation.feature.mainnavigato


import android.os.Bundle
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.BaseActivity
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.databinding.ActivityMainBinding
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.widget.actionbar.ActionBarExt
import com.example.qltvkotlin.presentation.widget.actionbar.ActionBarTitleAndSearchSate
import com.example.qltvkotlin.presentation.helper.StackNavigator

class MainActivity : BaseActivity(R.layout.activity_main) {
    lateinit var actionBarExt: ActionBarExt
    lateinit var stackNavigator: StackNavigator
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

