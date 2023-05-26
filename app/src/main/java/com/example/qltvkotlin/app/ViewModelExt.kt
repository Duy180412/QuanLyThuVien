package com.example.qltvkotlin.app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner


inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(): T {
    return ViewModelProvider(this)[T::class.java]
}

inline fun <reified T : ViewModel> FragmentActivity.viewModel(): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { getViewModel<T>() }


inline fun <reified T : ViewModel> Fragment.viewModel(): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { getViewModel<T>() }