package com.example.qltvkotlin.presentation.feature.addsach

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentAddSachBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.SelecPhoto
import com.example.qltvkotlin.domain.usecase.FieldsChangeCase
import com.example.qltvkotlin.domain.usecase.LuuSachCase
import com.example.qltvkotlin.domain.usecase.SelectPhotoCase
import com.example.qltvkotlin.presentation.app.BaseFragmentNavigation
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.helper.FetchAddSachCaseFields
import com.example.qltvkotlin.presentation.helper.FetchCaseFields
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter


class AddSachFragment : BaseFragmentNavigation(R.layout.fragment_add_sach) {

    private val binding by viewBinding { FragmentAddSachBinding.bind(this) }
    override val viewModel by viewModels<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ComponentAdapter(binding.rycView)
        viewModel.components.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        adapter.onCommand = {
            viewModel.execute(it)
        }
    }

    override fun editOrSaveCase(it: View) {
        viewModel.save()
    }

    override fun getCheck(): () -> Boolean {
        return { viewModel.hasChange() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.tryFetch()
    }

    class VM(
        private val fetchCaseFields: FetchCaseFields = FetchAddSachCaseFields(),
        private val selecPhoto: SelectPhotoCase = SelectPhotoCase(),
        private val luuSachCase: LuuSachCase = LuuSachCase(),
        private val fieldsChangeCase: FieldsChangeCase = FieldsChangeCase()
    ) : BaseViewModel() {
        val components = fetchCaseFields.result

        fun tryFetch() {
            if (fetchCaseFields.shouldFetch()) launch {
                fetchCaseFields()
            }
        }

        fun execute(it: Command) {
            when (it) {
                is SelecPhoto -> launch { selecPhoto(it.item) }
            }
        }

        fun save() {
            launch(error) {
                luuSachCase(components.value.orEmpty())
                successAndFinish.postValue("")
            }
        }

        fun hasChange(): Boolean {
            return fieldsChangeCase(components.value)
        }
    }
}

