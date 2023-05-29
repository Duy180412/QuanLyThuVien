package com.example.qltvkotlin.feature.presentation.router

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.feature.helper.Arguments
import kotlin.reflect.KClass

interface Routing : Arguments {
}

interface ActivityRouting : Routing {
    val clazzActivity: KClass<out Activity>
}

interface FragmentRouting : Routing {
    val clazzFragment: KClass<out Fragment>
}