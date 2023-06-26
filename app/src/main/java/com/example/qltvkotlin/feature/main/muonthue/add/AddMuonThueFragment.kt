package com.example.qltvkotlin.feature.main.muonthue.add

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentNavigation
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding


class AddMuonThueFragment : BaseFragmentNavigation(R.layout.fragment_add_muon_thue) {
    private val binding by viewBinding { FragmentAddMuonThueBinding.bind(this) }
    private val viewmodel by viewModel<VM>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun clickEditAndSave(it: View) {
        TODO("Not yet implemented")
    }

    override fun getRun(): () -> Unit {
        TODO("Not yet implemented")
    }

    override fun getCheck(): () -> Boolean {
        TODO("Not yet implemented")
    }

    class VM:ViewModel()
}