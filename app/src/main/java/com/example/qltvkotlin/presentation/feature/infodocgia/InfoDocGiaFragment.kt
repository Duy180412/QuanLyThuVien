package com.example.qltvkotlin.presentation.feature.infodocgia

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.BaseFragmentNavigation
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.databinding.FragmentInfoDocGiaBinding
import com.example.qltvkotlin.presentation.app.BaseFragment
import com.example.qltvkotlin.presentation.router.Routes
import com.example.qltvkotlin.presentation.helper.lazyArgument

class InfoDocGiaFragment : BaseFragmentNavigation(R.layout.fragment_info_doc_gia) {
    private val binding by viewBinding { FragmentInfoDocGiaBinding.bind(this) }
    override val viewModel by viewModels<VM>()
    private val args = lazyArgument<Routes.InfoDocGia>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    }

}


