package com.example.qltvkotlin.presentation.app

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.show
import com.example.qltvkotlin.presentation.helper.Results
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter

abstract class FragmentRecyclerView(contentLayoutId: Int) : BaseFragmentMain(contentLayoutId) {
    abstract override val viewModel: FragmentViewVM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.actionBarMain.search.observe(viewLifecycleOwner) {
            viewModel.search(it)
        }

    }

    abstract class FragmentViewVM : BaseViewModel() {
        abstract fun search(it: String)

    }
}