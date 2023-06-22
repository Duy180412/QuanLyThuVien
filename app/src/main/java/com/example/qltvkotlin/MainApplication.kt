package com.example.qltvkotlin

import android.app.Application
import com.example.qltvkotlin.datasource.roomdata.AppDataBase
import com.example.qltvkotlin.feature.presentation.app.AppFileManager


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDataBase.init(this)
        AppFileManager.init(this)
    }
}