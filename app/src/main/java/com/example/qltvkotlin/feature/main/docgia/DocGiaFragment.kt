package com.example.qltvkotlin.feature.main.docgia

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.FragmentRecyclerView
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewmodel
import com.example.qltvkotlin.databinding.FragmentDocGiaBinding
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.feature.main.adapter.DocGiaApdater
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.extension.show
import com.example.qltvkotlin.feature.presentation.router.Router
import com.example.qltvkotlin.feature.presentation.router.Routes

class DocGiaFragment : FragmentRecyclerView(R.layout.fragment_doc_gia) {
    private val binding by viewBinding { FragmentDocGiaBinding.bind(this) }
    override val viewmodel by viewmodel<VM>()

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
            viewmodel.xoa(it)
        }
        viewmodel.listSearch.observe(viewLifecycleOwner){
            adapter.setList(it)
            show(binding.rong,it.isEmpty())
        }
    }


    class VM :  FragmentViewVM(){
        private val docGiaRepo = DocGiaRepo.shared
        var listSearch = MutableLiveData<List<IDocGiaItem>>()
        override fun search(it: String) {
            searchType = it
            launch {
                listSearch.postValue(docGiaRepo.searchDocGia(it))
            }
        }

        override suspend fun repo(id: String): Boolean {
            return docGiaRepo.repoDocGia(id)
        }

        override suspend fun del(id: String): Boolean {
           return docGiaRepo.delDocGia(id)
        }
    }

}
