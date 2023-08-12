package com.example.qltvkotlin.presentation.feature.muonthue

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.FragmentRecyclerView
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewModel
import com.example.qltvkotlin.databinding.FragmentMuonthueViewBinding
import com.example.qltvkotlin.domain.enumeration.TypeSearch
import com.example.qltvkotlin.domain.usecase.SearchCase
import com.example.qltvkotlin.presentation.extension.show
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter

class DangThueFragment : FragmentViewMuonSach() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loaiShare = TypeSearch.ConHan
    }
}

class HetHanFragment : FragmentViewMuonSach() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loaiShare =TypeSearch.HetHan
    }

}

abstract class FragmentViewMuonSach : FragmentRecyclerView(R.layout.fragment_muonthue_view) {
    private val binding by viewBinding { FragmentMuonthueViewBinding.bind(this) }
    override val viewModel by viewModel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ComponentAdapter(binding.rycView)
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
        private val searchCase: SearchCase = SearchCase(),
    ) : FragmentViewVM() {
        val search = searchCase.result
        var loaiShare:TypeSearch = TypeSearch.ConHan
        override fun search(it: String) {
            launch {
                searchCase(loaiShare, it)
            }
        }
        fun tryFetch() = launch { searchCase(loaiShare) }
    }

}