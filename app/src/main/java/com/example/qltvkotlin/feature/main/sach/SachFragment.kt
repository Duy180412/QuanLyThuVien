package com.example.qltvkotlin.feature.main.sach

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragment
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.FragmentSachBinding
import com.example.qltvkotlin.domain.model.IStringSearch
import com.example.qltvkotlin.feature.actionbar.ActionBarExt
import com.example.qltvkotlin.feature.main.adapter.SachApdater
import com.example.qltvkotlin.feature.main.adapter.SearchExt


class SachFragment : BaseFragment(R.layout.fragment_sach) {
    private val binding by viewBinding { FragmentSachBinding.bind(this) }
    private val viewmodel by viewModel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apdater = SachApdater(binding.rycView)
        val actionBarExt = ActionBarExt(binding.actionBar, this::class)
        actionBarExt.search.observe(viewLifecycleOwner) {
            viewmodel.search(it)
        }
    }

    class VM(private val searchExt: SearchExt = SearchExt()) : ViewModel() {
        fun search(it: IStringSearch) = searchExt.search(it)

    }

}
