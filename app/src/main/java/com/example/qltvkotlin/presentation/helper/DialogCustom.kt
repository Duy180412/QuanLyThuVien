package com.example.qltvkotlin.presentation.helper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.qltvkotlin.R
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewModel
import com.example.qltvkotlin.databinding.DialogCustomBinding
import com.example.qltvkotlin.domain.datastructure.pairLookupOf
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.HasCommandCallback
import com.example.qltvkotlin.domain.enumeration.TypeSearch
import com.example.qltvkotlin.domain.model.IItemSpinner
import com.example.qltvkotlin.domain.usecase.SearchCase
import com.example.qltvkotlin.presentation.app.BaseFragment
import com.example.qltvkotlin.presentation.widget.actionbar.ActionBarExt
import com.example.qltvkotlin.presentation.widget.actionbar.ActionBarTitleAndSearchSate
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter

class DocGiaSelecDialog(activity: AppCompatActivity) :
    DialogCustom(activity, R.string.doc_gia_item)

class SachSelecDialog(activity: AppCompatActivity) :
    DialogCustom(activity, R.string.sach_item)

abstract class DialogCustom(
    private val mActivity: AppCompatActivity,
    private val resId: Int
) : DialogFragment(), HasCommandCallback {
    private var binding: DialogCustomBinding? = null
    private val viewModel by viewModel<VM>()
    override var onCommand: (Command) -> Unit = {}
    private val routing = pairLookupOf(
        R.string.sach_item to TypeSearch.SachItemSpinner,
        R.string.doc_gia_item to TypeSearch.DocGiaItemSpinner
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.typeSearch = routing.requireValueOf(resId)
        binding = DialogCustomBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBarExt = ActionBarExt(binding!!.topbar.containertopbar)
        val actionSearch = ActionBarTitleAndSearchSate(resId, actionBarExt)
        val adapter = ComponentAdapter(binding!!.rycView)
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        actionSearch.search.observe(viewLifecycleOwner) {
            viewModel.search(it)
        }
        adapter.onCommand = {
            this.onCommand(it)
            dismiss()
        }

    }

    fun showDialog(function: (Command) -> Unit) {
        onCommand = function
        this.show(mActivity.supportFragmentManager, "DialogCustom")
    }


//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = super.onCreateDialog(savedInstanceState)
//        dialog.setCanceledOnTouchOutside(false)
//        return dialog
//    }

    override fun onResume() {
        viewModel.tryFetch()
        super.onResume()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    class VM(
        private val searchCase: SearchCase = SearchCase()
    ) : BaseFragment.BaseViewModel() {
        var list = searchCase.result
        var typeSearch: TypeSearch = TypeSearch.None
        fun search(it: String) {
            launch(error) {
                searchCase(typeSearch, it)
            }
        }

        fun tryFetch() {
            launch(error) {
                searchCase(typeSearch)
            }
        }
    }


}
