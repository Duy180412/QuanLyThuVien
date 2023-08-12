package com.example.qltvkotlin.presentation.feature.account

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.BaseFragmentMain
import com.example.qltvkotlin.presentation.extension.viewModel
import com.example.qltvkotlin.data.datasource.SharedPreferencesExt
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.LogOut
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.router.Router
import com.example.qltvkotlin.presentation.router.Routes
import com.example.qltvkotlin.presentation.router.Routing


class AccountFragment : BaseFragmentMain(R.layout.fragment_account) {
    override val viewModel by viewModel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.actionBarExt.onCommand= {viewModel.actionCommand(it)}
        viewModel.login.observe(viewLifecycleOwner){
            Router.open(this, Routes.Login())
        }
    }


    class VM : BaseViewModel() {
        private val shared = SharedPreferencesExt.instance
        val login = MutableLiveData<Routing>()
        fun logOut() {
            shared.logOut()
            login.postValue(Routes.Login())
        }

        fun actionCommand(it: Command) {
            launch(error) {
                when (it){
                    is LogOut -> thongBao.postValue("logout")
                }
            }

        }
    }
}