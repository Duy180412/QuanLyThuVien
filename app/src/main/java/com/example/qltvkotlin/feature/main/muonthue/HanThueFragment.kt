package com.example.qltvkotlin.feature.main.muonthue

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragment
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.FragmentMuonthueViewBinding
import com.example.qltvkotlin.domain.model.IMuonSachItem
import com.example.qltvkotlin.domain.repo.MuonThueRepo
import com.example.qltvkotlin.feature.helper.Results
import com.example.qltvkotlin.feature.helper.Role
import com.example.qltvkotlin.feature.main.MainActivity
import com.example.qltvkotlin.feature.main.adapter.MuonSachApdater
import com.example.qltvkotlin.feature.presentation.extension.show

class DangThueFragment : MuonThueFragmentView() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.loaiShare = Role.DangThue
    }
}

class HetHanFragment : MuonThueFragmentView() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.loaiShare = Role.HetHan
    }

}

abstract class MuonThueFragmentView : BaseFragment(R.layout.fragment_muonthue_view) {
    private val binding by viewBinding { FragmentMuonthueViewBinding.bind(this) }
    val viewmodel by viewModel<VM>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MuonSachApdater(binding.rycView)
        val activity = requireActivity() as MainActivity
        activity.actionBarMain.search.observe(viewLifecycleOwner) {
            viewmodel.search(it)
        }
        adapter.onClickDel = {
            viewmodel.del(it)
        }

        viewmodel.search.observe(viewLifecycleOwner) {
            adapter.setList(it)
            show(binding.rong, it.isEmpty())
        }
    }

    override fun onResume() {
        super.onResume()
        viewmodel.startSearch()
    }

    class VM : ViewModel() {
        private val muonThueRepo = MuonThueRepo.shared
        var error = MutableLiveData<Throwable>()
        var search = MutableLiveData<List<IMuonSachItem>>()
        var result = MutableLiveData<Results<String>>()
        var loaiShare: Role = Role.DangThue
        private var searchType: String = ""
        fun search(it: String) {
            searchType = it
            launch(error) {
                search.postValue(muonThueRepo.search(it, loaiShare))
            }
        }

        fun startSearch() = search(searchType)
        fun del(cmnd: String) {
            result.value = Results.Loading("Đang Xóa Mã Độc Giả $cmnd")
            launch {
                val boolean = muonThueRepo.del(cmnd)
                if (boolean) result.postValue(Results.Success("Xóa $cmnd Thành Công", cmnd))
                else result.postValue(Results.Error("Mã Đọc Giả $cmnd Xóa Không Thành Công"))
            }
        }

    }

}