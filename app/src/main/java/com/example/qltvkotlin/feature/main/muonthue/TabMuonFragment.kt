package com.example.qltvkotlin.feature.main.muonthue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.FragmentRecyclerView
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.FragmentMuonthueViewBinding
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.domain.repo.MuonThueRepo
import com.example.qltvkotlin.feature.helper.Role
import com.example.qltvkotlin.feature.main.adapter.MuonSachApdater
import com.example.qltvkotlin.feature.presentation.extension.show

class DangThueFragment : FragmentViewMuonSach() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.loaiShare = Role.DangThue
    }
}

class HetHanFragment : FragmentViewMuonSach() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.loaiShare = Role.HetHan
    }

}

abstract class FragmentViewMuonSach : FragmentRecyclerView(R.layout.fragment_muonthue_view) {
    private val binding by viewBinding { FragmentMuonthueViewBinding.bind(this) }
    val viewmodel by viewModel<VM>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodelMain = viewmodel
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MuonSachApdater(binding.rycView)
        adapter.onClickDel = {
            viewmodel.xoa(it)
        }

        viewmodel.search.observe(viewLifecycleOwner) {
            adapter.setList(it)
            show(binding.rong, it.isEmpty())
        }
    }
    class VM : FragmentViewVM() {
        private val muonThueRepo = MuonThueRepo.shared
        var search = MutableLiveData<List<IMuonSachItem>>()
        var loaiShare: Role = Role.DangThue
        override fun search(it: String) {
            searchType = it
            launch {
                search.postValue(muonThueRepo.search(it, loaiShare))
            }
        }

        override suspend fun repo(id: String): Boolean {
           return muonThueRepo.repo(id)
        }

        override suspend fun del(id: String): Boolean {
            return muonThueRepo.del(id)
        }

    }

}