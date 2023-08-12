package com.example.qltvkotlin.presentation.feature.docgia

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentDocGiaBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.KhoiPhucDocGia
import com.example.qltvkotlin.domain.enumeration.OpenInfoDocGia
import com.example.qltvkotlin.domain.enumeration.TypeSearch
import com.example.qltvkotlin.domain.enumeration.XoaDocGia
import com.example.qltvkotlin.domain.helper.AppNavigator
import com.example.qltvkotlin.domain.usecase.SearchCase
import com.example.qltvkotlin.domain.usecase.docgia.KhoiPhucDocGiaCase
import com.example.qltvkotlin.domain.usecase.docgia.XoaDocGiaCase
import com.example.qltvkotlin.presentation.app.FragmentRecyclerView
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.extension.show
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewModel
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter


class DocGiaFragment : FragmentRecyclerView(R.layout.fragment_doc_gia) {
    private val binding by viewBinding { FragmentDocGiaBinding.bind(this) }
    override val viewModel by viewModel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ComponentAdapter(binding.rycView)
        binding.btnAdd.onClick {
            viewModel.open()
        }
        adapter.onCommand = { viewModel.actionCommand(it) }
        viewModel.search.observe(viewLifecycleOwner) {
            adapter.setList(it)
            show(binding.rong, it.isEmpty())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.tryFetch()
    }


    class VM(
        private val appNavigator: AppNavigator = AppNavigator.shared,
        private val searchCase: SearchCase = SearchCase(),
        private val xoaDocGiaCase: XoaDocGiaCase = XoaDocGiaCase(),
        private val khoiPhucDocGiaCase: KhoiPhucDocGiaCase = KhoiPhucDocGiaCase()
    ) : FragmentViewVM() {
        var search = searchCase.result
        override fun search(it: String) {
            launch {
                searchCase(TypeSearch.DocGia, it)
            }
        }

        fun open() = appNavigator.openAddDocGia()
        fun tryFetch() = launch { searchCase(TypeSearch.DocGia) }
        fun actionCommand(it: Command) {
            when (it) {
                is OpenInfoDocGia -> appNavigator.openInfoSach(it.item.cmnd)
                is XoaDocGia -> launch(error) {
                    xoaDocGiaCase(it.item, search.value.orEmpty()) {
                        actionCommand(it)
                    }
                }

                is KhoiPhucDocGia -> launch { khoiPhucDocGiaCase.invoke(it) }
            }
        }

    }

}

