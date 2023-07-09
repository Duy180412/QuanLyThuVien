package com.example.qltvkotlin.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.domain.model.MessageShowOwner
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.domain.repo.SachRepo
import com.example.qltvkotlin.feature.action.TakePhotoActionOwner
import com.example.qltvkotlin.feature.helper.OnBackClick
import com.example.qltvkotlin.feature.helper.Results
import com.example.qltvkotlin.feature.main.MainActivity
import com.example.qltvkotlin.feature.main.docgia.DocGiaFragment
import com.example.qltvkotlin.feature.main.mainnavigato.MainNavigationActivity
import com.example.qltvkotlin.feature.main.muonthue.add.DialogProvider
import com.example.qltvkotlin.feature.presentation.app.AppPermissionOwer
import com.example.qltvkotlin.widget.view.ToastFactoryOwner

abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId),
    AppPermissionOwer, ToastFactoryOwner, MessageShowOwner {
    val dialog = DialogProvider.shared

    fun thongBaoToast(it: String) {
        toast.invoke(it)
    }
}

abstract class FragmentRecyclerView(contentLayoutId: Int) : BaseFragmentMain(contentLayoutId) {
    lateinit var viewmodelMain: FragmentViewVM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity.actionBarMain.search.observe(viewLifecycleOwner) {
            viewmodelMain.search(it)
        }
        viewmodelMain.result.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Results.Loading -> result.message?.let { thongBaoToast(it) }
                is Results.Success -> {
                    result.value ?: return@observe
                    dialog.bottomUndo(requireView(), result.message) {
                        viewmodelMain.khoiPhuc(result.value)
                    }
                }

                is Results.Error -> result.message?.let { thongBaoToast(it) }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewmodelMain.restoreSearch()
    }

    abstract class FragmentViewVM : ViewModel() {
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

abstract class BaseFragmentMain(contentLayoutId: Int) : BaseFragment(contentLayoutId) {
    lateinit var mActivity: MainActivity
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mActivity = requireActivity() as MainActivity
        super.onViewCreated(view, savedInstanceState)
    }

}


abstract class BaseFragmentNavigation(contentLayoutId: Int) : BaseFragment(contentLayoutId),
    TakePhotoActionOwner {
    lateinit var mActivity: MainNavigationActivity
    private lateinit var onBackClick: OnBackClick
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainNavigationActivity
        onBackClick = OnBackClick(mActivity)
        onBackClickCustom()
        mActivity.actionBarView.onClickEditAndSave = { it -> clickEditAndSave(it) }
    }


    abstract fun clickEditAndSave(it: View)

    private fun onBackClickCustom() {
        onBackClick.checkValueWhenClickBack(
            funCheck = getCheck(),
            funRun = getRun()
        )
        mActivity.actionBarView.onClickBack = { onBackClick.handleOnBackPressed() }
        mActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackClick)
    }

    fun getRun(): () -> Unit {
        return { dialog.selectYesNo("Hủy Thêm", { mActivity.finish() }, {}) }
    }

    abstract fun getCheck(): () -> Boolean

}
