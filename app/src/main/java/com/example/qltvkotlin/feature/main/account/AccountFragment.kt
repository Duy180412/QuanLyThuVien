package com.example.qltvkotlin.feature.main.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentMain
import com.example.qltvkotlin.app.viewmodel
import com.example.qltvkotlin.datasource.SharedPreferencesExt
import com.example.qltvkotlin.feature.presentation.router.Router
import com.example.qltvkotlin.feature.presentation.router.Routes
import com.example.qltvkotlin.feature.presentation.router.Routing


class AccountFragment : BaseFragmentMain(R.layout.fragment_account) {
    override val viewmodel by viewmodel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.actionBarMain.logout = {
            viewmodel.logOut()
        }
        viewmodel.login.observe(viewLifecycleOwner){
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
    }
}