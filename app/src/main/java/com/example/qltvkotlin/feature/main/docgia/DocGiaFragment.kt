package com.example.qltvkotlin.feature.main.docgia

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragment
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.FragmentDocGiaBinding
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.feature.helper.Results
import com.example.qltvkotlin.feature.main.MainActivity
import com.example.qltvkotlin.feature.main.adapter.DocGiaApdater
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.extension.show
import com.example.qltvkotlin.feature.presentation.router.Router
import com.example.qltvkotlin.feature.presentation.router.Routes

class DocGiaFragment : BaseFragment(R.layout.fragment_doc_gia) {
    private val binding by viewBinding { FragmentDocGiaBinding.bind(this) }
    private val viewmodel by viewModel<VM>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MainActivity
        val adapter = DocGiaApdater(binding.rycView)
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
                    result.value ?: return@observe
                    dialogFactory.bottomUndo(requireView(), result.message) {
                        message.let {
                            viewmodel.khoiPhuc(result.value)
                            adapter.unDoItemList()
                        }
                    }
                }

                is Results.Error -> toast.invoke(result.message)
            }
        }
        binding.btnAdd.onClick {
            Router.open(this, Routes.AddDocGia())
        }
        adapter.onClickItem = {
            Router.open(this, Routes.InfoDocGia(it))
        }
        adapter.onClickDel = {
            viewmodel.delDocGia(it)
        }
    }



    override fun onResume() {
        super.onResume()
        viewmodel.startSearch()
    }

    class VM : ViewModel() {
        private val docGiaRepo = DocGiaRepo.docGiaRepo
        var result = MutableLiveData<Results<String>>()
        var search = MutableLiveData<List<IDocGiaItem>>()
        private var searchType:String = ""


        fun search(it: String) {
            searchType = it
            launch {
                search.postValue(docGiaRepo.searchDocGia(it))
            }
        }

        fun startSearch() = search(searchType)

        fun delDocGia(id: String) {
            result.value = Results.Loading("Đang Xóa Mã Độc Giả $id")
            launch {
                val boolean = docGiaRepo.delDocGia(id)
                if (boolean) result.postValue(Results.Success("Xóa $id Thành Công", id))
                else result.postValue(Results.Error("Mã Sách $id Xóa Không Thành Công"))
            }
        }

        fun khoiPhuc(id: String) {
            result.value = Results.Loading("Đang Khôi Phục Đọc Giả $id")
            launch {
                val boolean = docGiaRepo.repoDocGia(id)
                if (boolean) result.postValue(Results.Success("Khôi Phục Thành Công", null))
                else result.postValue(Results.Error("Khôi Phục Thất Bại"))
            }
        }
    }

}
