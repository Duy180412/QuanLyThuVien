package com.example.qltvkotlin.feature.main


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseActivity
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.ActivityMainBinding
import com.example.qltvkotlin.feature.actionbar.ActionBarExt
import com.example.qltvkotlin.feature.main.account.AccountFragment
import com.example.qltvkotlin.feature.main.docgia.DocGiaFragment
import com.example.qltvkotlin.feature.main.muonthue.MuonThueFragment

import com.example.qltvkotlin.feature.main.sach.SachFragment
import com.example.qltvkotlin.feature.presentation.extension.pairLookupOf

import kotlin.reflect.KClass

class MainActivity : BaseActivity(R.layout.activity_main) {
    private val binding by viewBinding { ActivityMainBinding.bind(this) }
    private val navigator = supportFragmentManager
    private val routing = pairLookupOf<Int, KClass<out Fragment>>(
        R.string.sach to SachFragment::class,
        R.string.account to AccountFragment::class,
        R.string.doc_gia to DocGiaFragment::class,
        R.string.muon_thue to MuonThueFragment::class
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabAdapter = BottomNavigatorMenuMain(binding.mainNavigation)
        if (savedInstanceState == null) {
            navigateTo(tabAdapter.selectedItemId)
        }
        tabAdapter.setOnItemSelectedListener { itemId ->
            val fragment = navigator.fragments.first()
            val currentId = routing.requireKeyOf(fragment::class)
            if (currentId != itemId) {
                navigateTo(itemId)
                true
            } else false
        }


    }

    private fun navigateTo(selectedItemId: Int) {
        val clazz = routing.requireValueOf(selectedItemId)
        val fragment = navigator.fragmentFactory.instantiate(
            classLoader,
            clazz.qualifiedName!!
        )
        navigator.beginTransaction().replace(binding.content.id, fragment)
            .commitAllowingStateLoss()
    }
}