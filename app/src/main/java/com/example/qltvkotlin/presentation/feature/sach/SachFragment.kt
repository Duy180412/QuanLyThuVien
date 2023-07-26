package com.example.qltvkotlin.presentation.feature.sach

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.app.FragmentRecyclerView
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewmodel
import com.example.qltvkotlin.databinding.FragmentSachBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.OnClickDel
import com.example.qltvkotlin.domain.enumeration.OnClickItem
import com.example.qltvkotlin.domain.helper.AppNavigator
import com.example.qltvkotlin.domain.model.ISachItem
import com.example.qltvkotlin.domain.repo.SachRepo
import com.example.qltvkotlin.presentation.extension.onClick
import com.example.qltvkotlin.presentation.extension.post
import com.example.qltvkotlin.presentation.extension.show


class SachFragment : FragmentRecyclerView(R.layout.fragment_sach) {
    private val binding by viewBinding { FragmentSachBinding.bind(this) }
    override val viewModel by viewmodel<VM>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SachApdater(binding.rycView)
        viewModel.search.observe(viewLifecycleOwner) {
            show(binding.rong, it.isEmpty())
            adapter.setList(it)
        }
        binding.btnAdd.onClick {
           viewModel.open()
        }
        adapter.onCommand = { viewModel.actionCommand(it) }
    }


    class VM(
//        private val searchSachCase: SearchSachCase = SearchSachCase(),
        private val appNavigator: AppNavigator = AppNavigator.shared,
        private val sachRepo: SachRepo = SachRepo()
    ) : FragmentViewVM() {
        var search = MutableLiveData<List<ISachItem>>()

        fun actionCommand(it: Command) {
            when (it) {
                is OnClickItem -> appNavigator.openInfoSach(it.key)
                is OnClickDel -> xoa(it.key)
                else -> return
            }
        }

        override fun search(it: String) {
            keySearch = it
            launch { search.post(sachRepo.searchSach(it))}
        }

        override suspend fun repo(id: String): Boolean {
            return sachRepo.repoSach(id)
        }

        override suspend fun del(id: String): Boolean {
            return sachRepo.delSach(id)
        }

        fun open() = appNavigator.openAddSach()
    }
}
//
//class SearchSachCase(private val sachRepo: SachRepo = SachRepo()) {
//    val result = MutableLiveData<List<ISachItem>>()
//    suspend operator fun invoke(it:String){
//
//    }
//
//}
//
