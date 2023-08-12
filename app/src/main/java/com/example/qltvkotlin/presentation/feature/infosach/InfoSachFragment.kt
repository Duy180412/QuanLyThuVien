package com.example.qltvkotlin.presentation.feature.infosach

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.BaseFragmentNavigation
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.databinding.FragmentInfoSachBinding
import com.example.qltvkotlin.presentation.helper.lazyArgument
import com.example.qltvkotlin.presentation.router.Routes


class InfoSachFragment : BaseFragmentNavigation(R.layout.fragment_info_sach) {
    private val binding by viewBinding { FragmentInfoSachBinding.bind(this) }
    override val viewModel by viewModels<VM>()
    private val args = lazyArgument<Routes.InfoSach>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setSach(args.value?.id)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun getCheck(): () -> Boolean {
        return { true }
    }


    override fun editOrSaveCase(it: View) {

    }


    class VM : BaseViewModel() {

        fun setSach(id: CharSequence?) {

        }
    }
}