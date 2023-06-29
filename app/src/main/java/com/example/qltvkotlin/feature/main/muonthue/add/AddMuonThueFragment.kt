package com.example.qltvkotlin.feature.main.muonthue.add

import android.os.Bundle
import android.view.View

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentNavigation
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding
import com.example.qltvkotlin.feature.helper.Role
import com.example.qltvkotlin.feature.main.help.dialogcustom.DialogCustom
import com.example.qltvkotlin.feature.presentation.extension.onClick


class AddMuonThueFragment : BaseFragmentNavigation(R.layout.fragment_add_muon_thue) {
    private val binding by viewBinding { FragmentAddMuonThueBinding.bind(this) }
    private val viewmodel by viewModel<VM>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialogCustom = DialogCustom(Role.DocGia)
        binding.themdocgia.setOnClickListener{
            dialogCustom.show(requireActivity().supportFragmentManager,"MyDiaLog")
        }
    }

    override fun clickEditAndSave(it: View) {
        TODO("Not yet implemented")
    }

    override fun getRun(): () -> Unit {
        return {}
    }

    override fun getCheck(): () -> Boolean {
        return {true}
    }

    class VM:ViewModel()
}