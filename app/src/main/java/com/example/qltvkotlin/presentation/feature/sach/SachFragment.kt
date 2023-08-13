package com.example.qltvkotlin.presentation.feature.sach

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentRecyclerviewCustomBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.KhoiPhucSach
import com.example.qltvkotlin.domain.enumeration.OpenInfoSach
import com.example.qltvkotlin.domain.enumeration.TypeSearch
import com.example.qltvkotlin.domain.enumeration.XoaSach
import com.example.qltvkotlin.domain.helper.AppNavigator
import com.example.qltvkotlin.domain.usecase.SearchCase
import com.example.qltvkotlin.domain.usecase.sach.KhoiPhucSachCase
import com.example.qltvkotlin.domain.usecase.sach.XoaSachCase
import com.example.qltvkotlin.presentation.app.FragmentRecyclerView
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.extension.viewBinding

sion.viewBinding
import com.example.qltvkotlin.presentation.extension.viewModel
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter


class SachFragment : FragmentRecyclerView(R.layout.fragment_recyclerview_custom) {
    private val binding by viewBinding { FragmentRecyclerviewCustomBinding.bind(this) }
    override val viewModel by viewModel<VM>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ComponentAdapter(binding.rycView.rycView)
        viewModel.search.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        binding.btnAdd.onClick {
            viewModel.open()
        }
        adapter.onCommand = { viewModel.actionCommand(it) }
    }

    override fun onResume() {
        super.onResume()
        viewModel.tryFetch()
    }

    class VM(
        private val appNavigator: AppNavigator = AppNavigator.shared,
        private val searchCase: SearchCase = SearchCase(),
        private val xoaSachCase: XoaSachCase = XoaSachCase(),
        private val khoiPhucSachCase: KhoiPhucSachCase = KhoiPhucSachCase()
    ) : FragmentViewVM() {
        val search = searchCase.result

        fun actionCommand(it: Command) {
            when (it) {
                is OpenInfoSach -> appNavigator.openInfoSach(it.item.maSach)
                is XoaSach -> launch {
                    xoaSachCase(it.item, search.value.orEmpty()) {
                        actionCommand(it)
                    }
                }

                is KhoiPhucSach -> launch { khoiPhucSachCase.invoke(it) }
                else -> return
            }
        }

        override fun search(it: String) {
            launch(error) { searchCase(TypeSearch.Sach, it) }
        }
        fun open() = appNavigator.openAddSach()
        fun tryFetch() = launch { searchCase(TypeSearch.Sach) }
    }
}



