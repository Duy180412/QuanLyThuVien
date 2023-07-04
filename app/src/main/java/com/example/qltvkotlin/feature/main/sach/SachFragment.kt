package com.example.qltvkotlin.feature.main.sach

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragment
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel

import com.example.qltvkotlin.databinding.FragmentSachBinding
import com.example.qltvkotlin.domain.model.ISachItem
import com.example.qltvkotlin.domain.repo.SachRepo
import com.example.qltvkotlin.feature.helper.Results
import com.example.qltvkotlin.feature.main.MainActivity
import com.example.qltvkotlin.feature.main.adapter.SachApdater
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.extension.show
import com.example.qltvkotlin.feature.presentation.router.Router
import com.example.qltvkotlin.feature.presentation.router.Routes


class SachFragment : BaseFragment(R.layout.fragment_sach) {
    private val binding by viewBinding { FragmentSachBinding.bind(this) }
    private val viewmodel by viewModel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SachApdater(binding.rycView)
        val activity = requireActivity() as MainActivity
        activity.actionBarMain.search.observe(viewLifecycleOwner) {
            viewmodel.search(it)
        }
        viewmodel.search.observe(viewLifecycleOwner) {
            show(binding.rong, it.isEmpty())
            adapter.setList(it)
        }
        viewmodel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Loading -> toast.invoke(result.message)
                is Results.Success -> {
                    if (result.value.isNullOrEmpty()) {
                        adapter.unDoItemList()
                        return@observe
                    }
                    dialogFactory.bottomUndo(requireView(), result.message) {
                        message.let {
                            viewmodel.khoiPhuc(result.value)
                        }
                    }
                }

                is Results.Error -> toast.invoke(result.message)
            }
        }
        binding.btnAdd.onClick {
            Router.open(this, Routes.AddSach())
        }
        adapter.onClickItem = {
            Router.open(this, Routes.InfoSach(it))
        }
        adapter.onClickDel = {
            viewmodel.delBook(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewmodel.startSearch()
    }

    class VM : ViewModel() {
        private val sachRepo = SachRepo.shared
        var result = MutableLiveData<Results<String>>()
        var search = MutableLiveData<List<ISachItem>>()
        private var searchType: String = ""


        fun search(it: String) {
            searchType = it
            launch {
                search.postValue(sachRepo.searchSach(it))
            }
        }

        fun startSearch() = search(searchType)

        fun delBook(id: String) {
            result.value = Results.Loading("Đang Xóa Mã Sách $id")
            launch {
                val boolean = sachRepo.delSach(id)
                if (boolean) result.postValue(Results.Success("Xóa $id Thành Công", id))
                else result.postValue(Results.Error("Mã Sách $id Xóa Không Thành Công"))
            }
        }

        fun khoiPhuc(id: String) {
            result.value = Results.Loading("Đang Khôi Phục Sách $id")
            launch {
                val boolean = sachRepo.repoSach(id)
                if (boolean) result.postValue(Results.Success("Khôi Phục Thành Công", null))
                else result.postValue(Results.Error("Khôi Phục Thất Bại"))
            }
        }
    }

}

