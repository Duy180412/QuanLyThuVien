package com.example.qltvkotlin.presentation.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(): T {
    return ViewModelProvider(this)[T::class.java]
}


inline fun <reified T : ViewModel> FragmentActivity.viewModel(): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { getViewModel() }


inline fun <reified T : ViewModel> Fragment.viewmodel(): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) { getViewModel() }

@Suppress("unchecked_cast")
fun ViewModel.launch(
    fail: LiveData<out Throwable>? = null,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(CoroutineExceptionHandler { _, e ->
        (fail as? MutableLiveData<Throwable>)?.postValue(e)
    }) {
        block()
    }
}