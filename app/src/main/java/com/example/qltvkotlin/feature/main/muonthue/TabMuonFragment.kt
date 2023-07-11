package com.example.qltvkotlin.feature.main.muonthue

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.FragmentRecyclerView
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewmodel
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
    override val viewmodel by viewmodel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MuonSachApdater(binding.rycView)
        adapter.onClickDel = {
            dialog.selectYesNo("Trả Toàn Bộ Sách",{viewmodel.xoa(it)},{})
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