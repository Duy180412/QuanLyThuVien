package com.example.qltvkotlin

import android.app.Application
import com.example.qltvkotlin.data.datasource.SharedPreferencesExt
import com.example.qltvkotlin.data.datasource.roomdata.AppDataBase
import com.example.qltvkotlin.domain.helper.ActivityRetriever
import com.example.qltvkotlin.presentation.helper.AppFileManager
import com.example.qltvkotlin.presentation.widget.HorizontalLineDecoration


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDataBase.init(this)
        AppFileManager.init(this)
        SharedPreferencesExt.instance.init(this)
        ActivityRetriever.shared = ActivityRetriever(this)
        HorizontalLineDecoration.shared = HorizontalLineDecoration(this)
    }
}