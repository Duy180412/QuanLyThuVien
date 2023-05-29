package com.example.qltvkotlin

import android.app.Application
import com.example.qltvkotlin.datasource.roomdata.AppDataBase

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDataBase.init(this)
    }
}