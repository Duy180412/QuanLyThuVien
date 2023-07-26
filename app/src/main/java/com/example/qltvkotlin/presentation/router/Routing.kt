package com.example.qltvkotlin.presentation.router

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.presentation.helper.Arguments
import kotlin.reflect.KClass

interface Routing : Arguments {
}

interface ActivityRouting : Routing {
    val clazzActivity: KClass<out Activity>
}

interface FragmentRouting : Routing {
    val clazzFragment: KClass<out Fragment>
}