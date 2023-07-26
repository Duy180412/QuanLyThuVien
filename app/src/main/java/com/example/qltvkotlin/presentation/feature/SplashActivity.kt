package com.example.qltvkotlin.presentation.feature

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.helper.AppNavigator
import com.example.qltvkotlin.domain.usecase.CheckLoginCase
import com.example.qltvkotlin.presentation.app.BaseActivity
import com.example.qltvkotlin.presentation.extension.viewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(R.layout.activity_start) {
    private val viewModel by viewModel<VM>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.start()
    }

    class VM(
        private val checkLoginCase: CheckLoginCase = CheckLoginCase(),
        private val appNavigator: AppNavigator = AppNavigator.shared
    ) : ViewModel() {

        fun start() {
            if (checkLoginCase()) {
                appNavigator.openMain()
                return
            } else appNavigator.openLogin()
        }
    }
}

