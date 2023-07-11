package com.example.qltvkotlin.feature.main.muonthue

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.databinding.FragmentMuonThueBinding
import com.example.qltvkotlin.feature.main.adapter.ViewPageAdapter
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.router.Router
import com.example.qltvkotlin.feature.presentation.router.Routes

class MuonSachFragment : Fragment(R.layout.fragment_muon_thue) {
    private val binding by viewBinding { FragmentMuonThueBinding.bind(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPageAdapter(this,binding.viewPager)
        adapter.setupWith(binding.tab)
        binding.btnAdd.onClick{
            Router.open(this,Routes.AddMuon())
        }
    }
}