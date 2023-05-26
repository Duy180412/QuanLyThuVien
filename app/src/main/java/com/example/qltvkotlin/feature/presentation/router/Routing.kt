package com.example.qltvkotlin.feature.presentation.router

import android.app.Activity
import kotlin.reflect.KClass

interface Routing
interface ActivityRouting : Routing {
    val clazz: KClass<out Activity>
}