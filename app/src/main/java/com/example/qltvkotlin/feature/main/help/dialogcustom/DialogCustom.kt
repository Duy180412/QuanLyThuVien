package com.example.qltvkotlin.feature.main.help.dialogcustom

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.DialogCustomBinding
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.domain.repo.SachRepo
import com.example.qltvkotlin.feature.actionbar.ActionBarExt
import com.example.qltvkotlin.feature.actionbar.ActionBarInputSearchState
import com.example.qltvkotlin.feature.actionbar.ActionBarNavigator
import com.example.qltvkotlin.feature.actionbar.ActionBarTitleAndSearchButtonState
import com.example.qltvkotlin.feature.helper.Role
import com.example.qltvkotlin.feature.helper.spinner.AdapterSpinnerCustom
import com.example.qltvkotlin.feature.helper.spinner.IItemSpinner
import com.example.qltvkotlin.feature.presentation.extension.pairLookupOf


class DocGiaSelecDialog(activity: AppCompatActivity) :
    DialogCustom(activity, Role.DocGia)

abstract class DialogCustom(
    private val activity: AppCompatActivity,
    private val role: Role
) : DialogFragment() {
    private var binding: DialogCustomBinding? = null
    private val viewmodel by viewModel<VM>()
    private lateinit var onClickList: (IItemSpinner) -> Unit
    private val routing = pairLookupOf(
        Role.DocGia to ActionBarNavigator(R.string.title_them_docgia, R.string.hint_seach_docgia),
        Role.Sach to ActionBarNavigator(R.string.title_them_docgia, R.string.hint_seach_docgia)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCustomBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBarExt = ActionBarExt(binding!!.topbar.containertopbar)
        val adapter = AdapterSpinnerCustom(binding!!.rycView)
        val actionBarNavigator = routing.requireValueOf(role)
        val tieuDe = ActionBarTitleAndSearchButtonState(actionBarNavigator.title)
        val timKiem = ActionBarInputSearchState(actionBarNavigator.hint)
        viewmodel.list.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        adapter.onClickItem = {
            onClickList.invoke(it)
            dismiss()
        }
        tieuDe.clickSearch = { actionBarExt.setState(timKiem) }
        timKiem.exitSearch = { actionBarExt.setState(tieuDe) }
        timKiem.onSearchListener = {
            viewmodel.role = role
            viewmodel.searh(it)
        }
        actionBarExt.setState(tieuDe)
    }

    fun showDialog(function: (IItemSpinner) -> Unit) {
        onClickList = function
        this.show(activity.supportFragmentManager, "DialogCustom")
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = super.onCreateDialog(savedInstanceState)
//        dialog.setCanceledOnTouchOutside(false)
//        return dialog
//    }

    override fun onResume() {
        viewmodel.startSearch(role)
        super.onResume()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    class VM : ViewModel() {
        private val sachRepo = SachRepo.sachRepo
        private val docGiaRepo = DocGiaRepo.docGiaRepo
        var list = MutableLiveData<List<IItemSpinner>>()
        var role = Role.None
        private var keySearh = ""
        fun searh(it: String) {
            keySearh = it
            Log.v("chaythu", it)
            launch {
                when (role) {
                    Role.DocGia -> list.postValue(docGiaRepo.getListItemSpinner(it))
                    Role.Sach -> list.postValue(sachRepo.getListItemSpinner(it))
                    else -> list.postValue(emptyList())
                }
            }
        }

        fun startSearch(role2: Role) {
            role = role2
            searh(keySearh)
        }
    }
}
