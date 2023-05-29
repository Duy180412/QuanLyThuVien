package com.example.qltvkotlin.feature.main.sach

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragment
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel

import com.example.qltvkotlin.databinding.FragmentSachBinding
import com.example.qltvkotlin.domain.model.ISachItem
import com.example.qltvkotlin.domain.model.IStringSearch
import com.example.qltvkotlin.domain.model.IsSachSearch
import com.example.qltvkotlin.feature.actionbar.ActionBarExt
import com.example.qltvkotlin.feature.actionbar.ActionBarTitleAndSearchSate
import com.example.qltvkotlin.feature.main.adapter.SachApdater
import com.example.qltvkotlin.feature.main.adapter.SearchExt
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.router.Router
import com.example.qltvkotlin.feature.presentation.router.Routes



@Suppress("UNCHECKED_CAST")
class SachFragment : BaseFragment(R.layout.fragment_sach) {
    private val binding by viewBinding { FragmentSachBinding.bind(this) }
    private val viewmodel by viewModel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apdater = SachApdater(binding.rycView)
        val actionBar = ActionBarTitleAndSearchSate(this::class)
        actionBar.search.observe(viewLifecycleOwner) {
            viewmodel.search(it)
        }
        viewmodel.search.observe(viewLifecycleOwner) {
            apdater.setList(it as List<ISachItem>)
        }
        binding.btnAdd.onClick {
            Router.open(this, Routes.AddSach())
        }

    }


    override fun onResume() {
        super.onResume()
        viewmodel.startsearch()
    }

    class VM : ViewModel() {
        private val searchExt = SearchExt.searchExt
        var search = searchExt.listSearch
        private var mSearch = object : IsSachSearch {
            override var mValueSach = ""
        }


        fun search(it: IStringSearch) {
            if (it is IsSachSearch) mSearch.mValueSach = it.mValueSach
            launch {
                searchExt.search(it)
            }
        }

        fun startsearch() {
            search(mSearch)
        }

    }

}
