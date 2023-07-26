package com.example.qltvkotlin.presentation.helper

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.datastructure.pairLookupOf
import com.example.qltvkotlin.presentation.feature.account.AccountFragment
import com.example.qltvkotlin.presentation.feature.docgia.DocGiaFragment
import com.example.qltvkotlin.presentation.feature.muonthue.MuonSachFragment
import com.example.qltvkotlin.presentation.feature.sach.SachFragment
import kotlin.reflect.KClass


class StackNavigator(
    private val manager: FragmentManager,
    private val frameLayoutId: Int
) {
    private val routing = pairLookupOf(
        R.string.sach to SachFragment::class,
        R.string.account to AccountFragment::class,
        R.string.doc_gia to DocGiaFragment::class,
        R.string.muon_thue to MuonSachFragment::class
    )

    fun navigateTo(clazz: KClass<out Fragment>, arguments: Bundle? = null) {
        manager.beginTransaction().apply {
            val lastFragment = getLastFragment()
            if (lastFragment != null) detach(lastFragment)
            add(frameLayoutId, clazz.java, arguments, createTag(clazz))
        }.commit()
    }

    private fun createTag(clazz: KClass<out Fragment>): String {
        return "${clazz.java.name}:tag:${System.currentTimeMillis()}"

    }

    private fun getLastFragment(): Fragment? {
        val list =  manager.fragments
        return list.lastFragmnet()
    }

    fun navigateTo(selectedItemId: Int) {
        val clazz = routing.requireValueOf(selectedItemId)
        navigateTo(clazz)
    }

    fun getIdLastFragment(): Int {
        val fragment = getLastFragment()!!
        return routing.requireKeyOf(fragment::class)
    }
}

fun List<Fragment>.lastFragmnet(): Fragment? {
    val listFragment = listOf(
        SachFragment::class,
        AccountFragment::class,
        DocGiaFragment::class,
        MuonSachFragment::class
    )
    for (i in lastIndex downTo 0) {
        val fragment = get(i)
        if (listFragment.contains(fragment::class)) {
            return fragment
        }
    }
    return null
}

