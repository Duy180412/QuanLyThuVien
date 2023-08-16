package com.example.qltvkotlin.presentation.feature.infosach

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentRecyclerViewBinding
import com.example.qltvkotlin.presentation.app.BaseFragmentNavigation
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.helper.FetchSachCaseFields
import com.example.qltvkotlin.presentation.helper.FetchCaseFields
import com.example.qltvkotlin.presentation.helper.lazyArgument
import com.example.qltvkotlin.presentation.router.Routes


class  InfoSachFragment : BaseFragmentNavigation(R.layout.fragment_recycler_view) {
    private val binding by viewBinding { FragmentRecyclerViewBinding.bind(this) }
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


    class VM(private val fetchCaseFields: FetchCaseFields = FetchSachCaseFields()) : BaseViewModel() {
        val component = fetchCaseFields.result
        fun setSach(id: CharSequence?) {

        }
    }
}