package com.example.qltvkotlin.feature.main.muonthue.add

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentNavigation
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding
import com.example.qltvkotlin.datasource.bo.MuonSachEdittable
import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.domain.model.IMuonSachGet
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.domain.model.bindCharOwner
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.model.checkConditionChar
import com.example.qltvkotlin.feature.main.adapter.DangKiMuonSachAdapter
import com.example.qltvkotlin.feature.main.help.dialogcustom.DocGiaSelecDialogOnwer
import com.example.qltvkotlin.feature.main.help.dialogcustom.SachSelecDialogOnwer
import com.example.qltvkotlin.feature.presentation.extension.bindTo
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.show


class AddMuonThueFragment : BaseFragmentNavigation(R.layout.fragment_add_muon_thue),
    DocGiaSelecDialogOnwer,SachSelecDialogOnwer {
    private val binding by viewBinding { FragmentAddMuonThueBinding.bind(this) }
    private val viewmodel by viewModel<VM>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindEditTextOnChange()
        val adapter = DangKiMuonSachAdapter(binding.rycView)
        adapter.openDialog = {sachDialog.showDialog {

        }}
        binding.themdocgia.setStartIconOnClickListener {
            docGiaDialog.showDialog {
                binding.themdocgianhap.setText(it.nameKey)
                viewmodel.setMaDocGia(it.key)
            }
        }
        viewmodel.newMuonThue.observe(viewLifecycleOwner) {
            val value = it.cast<IMuonSachSet>()!!.list
            adapter.setList(value)
            bindWhenOnChange(it)
        }
    }

    private fun bindEditTextOnChange() {
        val value = viewmodel.newMuonThue.value.cast<IMuonSachSet>()!!
        binding.themdocgianhap.bindTo { value.maDocGia }
    }

    private fun bindWhenOnChange(it: IMuonSach) {
        val value = it.cast<IMuonSachSet>()!!
        value.maDocGia.also { it2 ->
            checkAndShowError(it2, binding.themdocgia)
            bindCharOwner(this, it2) {
                checkAndShowError(it, binding.themdocgia)
                show(binding.rycView, checkConditionChar(it))
            }
        }

    }

    override fun clickEditAndSave(it: View) {
        TODO("Not yet implemented")
    }

    override fun getCheck(): () -> Boolean {
        return { true }
    }

    class VM : ViewModel() {
        var newMuonThue = MutableLiveData<IMuonSach>()

        fun setMaDocGia(key: String) {
            val value = newMuonThue.value.cast<IMuonSachSet>()
            value ?: return
            value.maDocGia.update(key)
        }

        init {
            newMuonThue.value = MuonSachEdittable(object : IMuonSachGet {})
        }
    }
}