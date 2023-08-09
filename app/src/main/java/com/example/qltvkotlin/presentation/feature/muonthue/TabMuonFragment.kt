package com.example.qltvkotlin.presentation.feature.main.muonthue

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.FragmentRecyclerView
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewModel
import com.example.qltvkotlin.databinding.FragmentMuonthueViewBinding
import com.example.qltvkotlin.domain.enumeration.Role
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.presentation.feature.muonthue.MuonSachApdater
import com.example.qltvkotlin.presentation.extension.show

class DangThueFragment : FragmentViewMuonSach() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loaiShare = Role.DangThue
    }
}

class HetHanFragment : FragmentViewMuonSach() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loaiShare = Role.HetHan
    }

}

abstract class FragmentViewMuonSach : FragmentRecyclerView(R.layout.fragment_muonthue_view) {
    private val binding by viewBinding { FragmentMuonthueViewBinding.bind(this) }
    override val viewModel by viewModel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MuonSachApdater(binding.rycView)
        adapter.onClickDel = {
            dialog.selectYesNo("Trả Toàn Bộ Sách",{viewModel.xoa(it)},{})
        }

        viewModel.search.observe(viewLifecycleOwner) {
            adapter.setList(it)
            show(binding.rong, it.isEmpty())
        }
    }
    class VM : FragmentViewVM() {
//        private val muonThueRepo = MuonThueRepo.shared
        var search = MutableLiveData<List<IMuonSachItem>>()
        var loaiShare: Role = Role.DangThue
        override fun search(it: String) {
            keySearch = it
            launch {
//                search.postValue(muonThueRepo.search(it, loaiShare))
            }
        }

        override suspend fun repo(id: String): Boolean {
//           return muonThueRepo.repo(id)
            return true
        }

        override suspend fun del(id: String): Boolean {
//            return muonThueRepo.del(id)
            return true
        }

    }

}