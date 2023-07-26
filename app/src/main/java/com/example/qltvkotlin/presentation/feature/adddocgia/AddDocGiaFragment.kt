package com.example.qltvkotlin.presentation.feature.adddocgia

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentAddDocGiaBinding
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.SelecPhoto
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.usecase.FieldsChangeCase
import com.example.qltvkotlin.domain.usecase.LuuDocGiaCase
import com.example.qltvkotlin.domain.usecase.SelectPhotoCase
import com.example.qltvkotlin.presentation.app.BaseFragmentNavigation
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.helper.FetchAddDocGiaCaseFields
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter

class AddDocGiaFragment : BaseFragmentNavigation(R.layout.fragment_add_doc_gia) {

    private val binding by viewBinding { FragmentAddDocGiaBinding.bind(this) }
    override val viewModel by viewModels<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ComponentAdapter(binding.rycView)
        viewModel.components.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        adapter.onCommand = { viewModel.execute(it) }
    }


    override fun editOrSaveCase(it: View) {
        viewModel.save()
    }


    override fun getCheck(): () -> Boolean {
        return { viewModel.hasChange()}
    }

    override fun onResume() {
        super.onResume()
        viewModel.tryFetch()
    }


    class VM(
        private val fetchCaseFields: FetchAddDocGiaCaseFields = FetchAddDocGiaCaseFields(),
        private val selecPhoto: SelectPhotoCase = SelectPhotoCase(),
        private val luuDocGiaCase: LuuDocGiaCase = LuuDocGiaCase(),
        private val fieldsChangeCase: FieldsChangeCase = FieldsChangeCase(),

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
                luuDocGiaCase(components.value.orEmpty())
                successAndFinish.postValue("")
            }
        }

        fun hasChange(): Boolean {
            return fieldsChangeCase(components.value)
        }
    }
}


