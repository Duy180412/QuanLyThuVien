package com.example.qltvkotlin.feature.helper

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.example.qltvkotlin.R
import com.example.qltvkotlin.feature.main.account.AccountFragment
import com.example.qltvkotlin.feature.main.docgia.DocGiaFragment
import com.example.qltvkotlin.feature.main.muonthue.MuonThueFragment
import com.example.qltvkotlin.feature.main.sach.SachFragment
import com.example.qltvkotlin.feature.presentation.extension.pairLookupOf
import kotlin.reflect.KClass

interface StackNavigatorOwner {
    val stackNavigator: StackNavigator
}

class StackNavigator(
    private val manager: FragmentManager,
    private val frameLayoutId: Int
) {
    private val routing = pairLookupOf<Int, KClass<out Fragment>>(
        R.string.sach to SachFragment::class,
        R.string.account to AccountFragment::class,
        R.string.doc_gia to DocGiaFragment::class,
        R.string.muon_thue to MuonThueFragment::class
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
        return list.lastFragmnet2()
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

fun <T> List<T>.lastFragmnet(): T? {
    if (isEmpty()) return null
    val lastIndex = this.lastIndex
    return when (this[lastIndex]) {
        is SupportRequestManagerFragment -> this.getOrNull(lastIndex - 1)
        else -> this.lastOrNull()
    }
}

fun List<Fragment>.lastFragmnet2(): Fragment? {
    val listFragment = listOf(
        SachFragment::class,
        AccountFragment::class,
        DocGiaFragment::class,
        MuonThueFragment::class
    )
    for (i in lastIndex downTo 0) {
        val fragment = get(i)
        if (listFragment.contains(fragment::class)) {
            return fragment
        }
    }
    return null
//    var lastFragment: Fragment? = null
//    if (this.isEmpty()) return lastFragment
//    for (i in this.lastIndex downTo 0) {
//        val fragmentClass = this[i]
//        if (listFragment.any { it == fragmentClass }) {
//            lastFragment = fragmentClass
//        }
//    }
//    return lastFragment
}

