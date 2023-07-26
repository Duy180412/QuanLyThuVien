package com.example.qltvkotlin.presentation.extension

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.MutableLiveData

@SuppressLint("RestrictedApi")
fun <T> MutableLiveData<T>.post(value: T?) {
    if (ArchTaskExecutor.getInstance().isMainThread) this.value = value
    else postValue(value)
}
