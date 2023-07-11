package com.example.qltvkotlin

import android.app.Application
import com.example.qltvkotlin.datasource.SharedPreferencesExt
import com.example.qltvkotlin.datasource.roomdata.AppDataBase
import com.example.qltvkotlin.feature.main.adapter.CustomViewItemList
import com.example.qltvkotlin.feature.main.muonthue.add.DialogProvider
import com.example.qltvkotlin.feature.presentation.app.AppFileManager


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDataBase.init(this)
        AppFileManager.init(this)
        SharedPreferencesExt.instance.init(this)
        DialogProvider.shared = DialogProvider(this)
        CustomViewItemList.item = CustomViewItemList(this)
    }
}