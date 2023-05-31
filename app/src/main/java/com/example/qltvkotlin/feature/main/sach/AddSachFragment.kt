package com.example.qltvkotlin.feature.main.sach

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragment
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.databinding.FragmentAddSachBinding
import com.example.qltvkotlin.datasource.bo.SachEdit
import com.example.qltvkotlin.domain.model.GetError
import com.example.qltvkotlin.domain.model.HasIsValid
import com.example.qltvkotlin.domain.model.ISachEdit
import com.example.qltvkotlin.domain.model.checkValidator
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.feature.presentation.extension.bindTo
import com.example.qltvkotlin.feature.presentation.extension.cast


class AddSachFragment : BaseFragment(R.layout.fragment_add_sach), Signal.Closable by Signal.Bags() {

    private val binding by viewBinding { FragmentAddSachBinding.bind(this) }
    private val viewModel by viewModels<VM>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.masachNhap.bindTo { viewModel.sachMoi.value?.maSach }
        binding.tensachNhap.bindTo { viewModel.sachMoi.value?.tenSach }
        viewModel.sachMoi.observe(viewLifecycleOwner, this::bind)
    }

    private fun bind(it: ISachEdit) {
        it.maSach.cast<Signal>()?.bind {
            checkValidator(it.maSach, binding.masach)
        }
    }

    class VM : ViewModel(){
        val sachMoi = SachEdit.instance.sachEdit
    }

}