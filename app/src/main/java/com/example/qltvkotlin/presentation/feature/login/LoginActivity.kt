package com.example.qltvkotlin.presentation.feature.login

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.BaseActivity
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewModel
import com.example.qltvkotlin.databinding.ActivityLoginBinding
import com.example.qltvkotlin.domain.model.HasIsValid
import com.example.qltvkotlin.domain.model.IAccount
import com.example.qltvkotlin.domain.repo.FetchAccountRepo
import com.example.qltvkotlin.domain.repo.LoginRepo
import com.example.qltvkotlin.presentation.extension.bindTo
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.router.Router
import com.example.qltvkotlin.presentation.router.Routing
import com.example.qltvkotlin.presentation.router.Routes

class LoginActivity : BaseActivity(R.layout.activity_login), Signal.Closable by Signal.Bags() {
    private val binding by viewBinding { ActivityLoginBinding.bind(this) }
    private val viewmodel by viewModel<VM>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.buttonlogin.onClick { viewmodel.login() }
        viewmodel.loginSuccess.observe(this, this::open)
        viewmodel.account.observe(this, this::bind)
        binding.idlogin.bindTo { viewmodel.account.value?.id }
        binding.pass.bindTo { viewmodel.account.value?.password }

    }

    private fun bind(it: IAccount) {
        binding.idlogin.setText(it.id)
        binding.pass.setText(it.password)
        it.id.cast<Signal>()?.bind {
            binding.idlogin.isActivated = !(it.id.cast<HasIsValid>()?.isValid ?: true)
        }
        it.password.cast<Signal>()?.bind {
            binding.pass.isActivated = !(it.password.cast<HasIsValid>()?.isValid ?: true)

        }
    }

    private fun open(routing: Routing) {
        Router.open(this, routing)
        finish()

    }

    class VM(
        private val loginRepo: LoginRepo = LoginRepo(),
        private val fetchAccountRepo: FetchAccountRepo = FetchAccountRepo()
    ) : ViewModel() {
        val account = fetchAccountRepo.result
        val loginSuccess = MutableLiveData<Routing>()

        init {
            fetchAccountRepo()
        }

        fun login() {
            val mValue = account.value ?: return
            if (loginRepo(mValue)) loginSuccess.postValue(Routes.Main())
        }
    }
}