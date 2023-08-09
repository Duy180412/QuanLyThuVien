package com.example.qltvkotlin.presentation.feature.docgia

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.FragmentRecyclerView
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewModel
import com.example.qltvkotlin.databinding.FragmentDocGiaBinding
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.feature.main.adapter.DocGiaApdater
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.extension.show
import com.example.qltvkotlin.presentation.router.Router
import com.example.qltvkotlin.presentation.router.Routes

class DocGiaFragment : FragmentRecyclerView(R.layout.fragment_doc_gia) {
    private val binding by viewBinding { FragmentDocGiaBinding.bind(this) }
    override val viewModel by viewModel<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DocGiaApdater(binding.rycView)
        binding.btnAdd.onClick {
            Router.open(this, Routes.AddDocGia())
        }
        adapter.onClickItem = {
            Router.open(this, Routes.InfoDocGia(it))
        }
        adapter.onClickDel = {
            viewModel.xoa(it)
        }
        viewModel.listSearch.observe(viewLifecycleOwner){
            adapter.setList(it)
            show(binding.rong,it.isEmpty())
        }
    }


    class VM(private val docGiaRepo: DocGiaRepo = DocGiaRepo.shared) :  FragmentViewVM(){
        var listSearch = MutableLiveData<List<IDocGiaItem>>()
        override fun search(it: String) {
            keySearch = it
            launch {
                listSearch.postValue(docGiaRepo.searchDocGia(it))
            }
        }
//
        override suspend fun repo(id: String): Boolean {
            return docGiaRepo.repoDocGia(id)

        }

        override suspend fun del(id: String): Boolean {
           return docGiaRepo.delDocGia(id)
        }
    }

}
