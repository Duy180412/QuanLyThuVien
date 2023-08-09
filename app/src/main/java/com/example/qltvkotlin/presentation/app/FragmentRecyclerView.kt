package com.example.qltvkotlin.presentation.app

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.helper.Results

abstract class FragmentRecyclerView(contentLayoutId: Int) : BaseFragmentMain(contentLayoutId) {
    abstract override val viewModel: FragmentViewVM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.actionBarMain.search.observe(viewLifecycleOwner) {
            viewModel.search(it)
        }
        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Loading -> result.message?.let { toast.invoke(it) }
                is Results.Success -> {
                    result.value ?: return@observe
                    dialog.bottomUndo( result.message) {
                        viewModel.khoiPhuc(result.value)
                    }
                }

                is Results.Error -> result.message?.let { toast.invoke(it) }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.restoreSearch()
    }

    abstract class FragmentViewVM : BaseViewModel() {
        var result = MutableLiveData<Results<String>>()
        var keySearch = ""
        abstract fun search(it: String)
        fun restoreSearch() {
            search(keySearch)
        }

        fun khoiPhuc(id: String) {
            result.value = Results.Loading()
            launch {
                val boolean = repo(id)
                if (boolean) {
                    result.postValue(Results.Success("Khôi Phục Thành Công", null))
                    restoreSearch()
                } else result.postValue(Results.Error())
            }
        }

        fun xoa(id: String) {
            result.value = Results.Loading()
            launch {
                val boolean = del(id)
                if (boolean) {
                    result.postValue(Results.Success("Xóa Thành Công", id))
                    restoreSearch()
                } else result.postValue(Results.Error())
            }
        }

        abstract suspend fun repo(id: String): Boolean
        abstract suspend fun del(id: String): Boolean

    }
}