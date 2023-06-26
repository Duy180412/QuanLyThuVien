package com.example.qltvkotlin.feature.main.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragment
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.FragmentAccountBinding
import com.example.qltvkotlin.databinding.FragmentDocGiaBinding
import com.example.qltvkotlin.datasource.SharedPreferencesExt
import com.example.qltvkotlin.feature.main.MainActivity
import com.example.qltvkotlin.feature.main.docgia.DocGiaFragment
import com.example.qltvkotlin.feature.presentation.router.Router
import com.example.qltvkotlin.feature.presentation.router.Routes
import com.example.qltvkotlin.feature.presentation.router.Routing


class AccountFragment : BaseFragment(R.layout.fragment_account) {
    private val viewmodel by viewModel<VM>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MainActivity
        activity.actionBarMain.logout = {
            viewmodel.logOut()
        }
        viewmodel.login.observe(viewLifecycleOwner){
            Router.open(this, Routes.Login())
        }
    }

    class VM : ViewModel() {
        private val shared = SharedPreferencesExt.instance
        val login = MutableLiveData<Routing>()
        fun logOut() {
            shared.logOut()
            login.postValue(Routes.Login())
        }
    }
}