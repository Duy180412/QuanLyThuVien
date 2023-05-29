package com.example.qltvkotlin.feature.main.sach

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragment
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.databinding.FragmentAddSachBinding


class AddSachFragment : BaseFragment(R.layout.fragment_add_sach) {
    private val binding by viewBinding { FragmentAddSachBinding.bind(this) }
    private val viewModel by viewModels<VM>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    class VM : ViewModel()
}