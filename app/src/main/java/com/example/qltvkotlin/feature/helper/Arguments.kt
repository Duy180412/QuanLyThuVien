package com.example.qltvkotlin.feature.helper

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.feature.presentation.extension.cast


interface Arguments : Parcelable {


    @Suppress("DEPRECATION")
    companion object {
        val KEY: String = Arguments::class.java.name
        fun getArgumentsFrom(extras: Bundle?): Arguments? {
            extras ?: return null
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                extras.getParcelable(KEY, Arguments::class.java)
            } else {
                extras.getParcelable(KEY)
            }
        }
    }

    fun toBundle(): Bundle {
        return Bundle().also { it.putParcelable(KEY, this) }
    }
}

inline fun <reified T : Arguments> Fragment.lazyArgument() = lazy(LazyThreadSafetyMode.NONE) {
    Arguments.getArgumentsFrom(this.arguments).cast<T>()
}
