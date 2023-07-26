package com.example.qltvkotlin.presentation.extension


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

fun <T : ViewBinding> AppCompatActivity.viewBinding(function: View.() -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        val contentView = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        function(contentView)
    }
inline fun <reified T : ViewBinding> FragmentActivity.bindingOf(crossinline bindingInflater: (LayoutInflater) -> T): T {
    return bindingInflater.invoke(layoutInflater)
}


fun <T : ViewBinding> ViewGroup.bindingOf(function: (LayoutInflater, ViewGroup, Boolean) -> T): T {
    return function(LayoutInflater.from(context), this, false)
}
inline fun <reified T : ViewBinding> DialogFragment.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
): Lazy<T?> {
    return lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }
}
inline fun <reified T : Activity> Fragment.getActivtyBase(): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) {
        requireActivity().cast<T>()
            ?: throw ClassCastException(" this Fragment ${this::class.java.simpleName} can't be cast to Activty ${T::class.java.simpleName}")
    }

fun <T : ViewBinding> Fragment.viewBinding(function: View.() -> T): Lazy<T> = object :
    Lazy<T>,
    LifecycleEventObserver {
    private var mValue: T? = null

    override val value: T
        get() {
            if (mValue == null) mValue = function(
                view ?: error(
                    "Fragment view is not created yet!, " +
                            "please ensure binding called at onViewCreated"
                )
            )
            return mValue!!
        }

    init {
        viewLifecycleOwnerLiveData.observe(this@viewBinding) {
            it?.lifecycle?.addObserver(this)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            source.lifecycle.removeObserver(this)
            mValue = null
        }
    }

    override fun isInitialized(): Boolean {
        return mValue != null
    }
}

