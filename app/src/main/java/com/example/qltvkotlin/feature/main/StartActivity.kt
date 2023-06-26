package com.example.qltvkotlin.feature.main

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseActivity
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.datasource.SharedPreferencesExt
import com.example.qltvkotlin.feature.presentation.router.Router
import com.example.qltvkotlin.feature.presentation.router.Routes
import com.example.qltvkotlin.feature.presentation.router.Routing

class StartActivity : BaseActivity(R.layout.activity_start) {
    private val viewmodel by viewModel<VM>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.start()
        viewmodel.main.observe(this, this::open)
        viewmodel.login.observe(this, this::open)
    }

    private fun open(routing: Routing) {
        Router.open(this, routing)
        finish()
    }


    class VM(private val checkLogin: CheckLogin = CheckLogin()) : ViewModel() {
        val main = MutableLiveData<Routing>()
        val login = MutableLiveData<Routing>()
        fun start() {
            if (checkLogin()) main.postValue(Routes.Main())
            else login.postValue(Routes.Login())
        }
    }
}

class CheckLogin {
    val shared = SharedPreferencesExt.instance
    operator fun invoke(): Boolean {
        return shared.isLogin()
    }
}