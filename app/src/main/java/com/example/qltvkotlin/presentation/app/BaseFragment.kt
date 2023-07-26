package com.example.qltvkotlin.presentation.app

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.presentation.helper.AppPermissionOwer
import com.example.qltvkotlin.presentation.widget.view.ToastFactoryOwner

abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId),
    AppPermissionOwer, ToastFactoryOwner {
    val dialog = DialogProvider.shared
    abstract val viewModel: BaseViewModel
    abstract val mActivity: Activity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(viewLifecycleOwner) { throws ->
            throws.message?.let { dialog.notification(it) }
        }
        viewModel.successAndFinish.observe(viewLifecycleOwner) {
            toast.invoke(it)
            mActivity.finish()
        }
        viewModel.thongBao.observe(viewLifecycleOwner) {
            toast.invoke(it)
        }
    }
    abstract class BaseViewModel : ViewModel() {

        val error = MutableLiveData<Throwable>()
        val successAndFinish = MutableLiveData<String>()
        val thongBao = MutableLiveData<String>()
    }
}

