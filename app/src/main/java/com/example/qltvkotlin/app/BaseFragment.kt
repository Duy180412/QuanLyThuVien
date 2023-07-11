package com.example.qltvkotlin.app

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.domain.model.MessageShowOwner
import com.example.qltvkotlin.feature.helper.Results
import com.example.qltvkotlin.feature.main.muonthue.add.DialogProvider
import com.example.qltvkotlin.feature.presentation.app.AppPermissionOwer
import com.example.qltvkotlin.widget.view.ToastFactoryOwner

abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId),
    AppPermissionOwer, ToastFactoryOwner, MessageShowOwner {
    val dialog = DialogProvider.shared
    abstract val viewmodel: BaseViewModel
    abstract val mActivity: Activity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.error.observe(viewLifecycleOwner) { throws ->
            throws.message?.let { dialog.notification(it) }
        }
        viewmodel.successAndFinish.observe(viewLifecycleOwner) {
            thongBaoToast(it)
            mActivity.finish()
        }
        viewmodel.thongBao.observe(viewLifecycleOwner) {
            thongBaoToast(it)
        }
    }

    fun thongBaoToast(it: String) {
        toast.invoke(it)
    }

    abstract class BaseViewModel : ViewModel() {
        val error = MutableLiveData<Throwable>()
        val successAndFinish = MutableLiveData<String>()
        val thongBao = MutableLiveData<String>()
    }
}

abstract class FragmentRecyclerView(contentLayoutId: Int) : BaseFragmentMain(contentLayoutId) {
    abstract override val viewmodel: FragmentViewVM


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.actionBarMain.search.observe(viewLifecycleOwner) {
            viewmodel.search(it)
        }
        viewmodel.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Loading -> result.message?.let { thongBaoToast(it) }
                is Results.Success -> {
                    result.value ?: return@observe
                    dialog.bottomUndo(requireView(), result.message) {
                        viewmodel.khoiPhuc(result.value)
                    }
                }

                is Results.Error -> result.message?.let { thongBaoToast(it) }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewmodel.restoreSearch()
    }

    abstract class FragmentViewVM : BaseViewModel() {
        var result = MutableLiveData<Results<String>>()
        var searchType = ""
        abstract fun search(it: String)
        fun restoreSearch() {
            search(searchType)
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

