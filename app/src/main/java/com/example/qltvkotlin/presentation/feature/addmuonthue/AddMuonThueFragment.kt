package com.example.qltvkotlin.presentation.feature.addmuonthue

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding
import com.example.qltvkotlin.domain.enumeration.AddFeildThemSachDangKi
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.SelecItemStartTextLayout
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.usecase.SelecDocGiaMuonSachCase
import com.example.qltvkotlin.presentation.app.BaseFragmentNavigation
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewmodel
import com.example.qltvkotlin.presentation.helper.FetchAddMuonSachCaseFields
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter


class AddMuonThueFragment : BaseFragmentNavigation(R.layout.fragment_add_muon_thue) {
    private val binding by viewBinding { FragmentAddMuonThueBinding.bind(this) }
    override val viewModel by viewmodel<VM>()
    override fun editOrSaveCase(it: View) {
    }

    override fun getCheck(): () -> Boolean {
        return { true }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ComponentAdapter(binding.rycView)
        viewModel.component.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        adapter.onCommand = {
            viewModel.execute(it)
        }


    }

    override fun onResume() {
        super.onResume()
        viewModel.tryFetch()
    }

    class VM(
        private val fetchCaseFields: FetchAddMuonSachCaseFields = FetchAddMuonSachCaseFields(),
        private val selectDocGiaMuonSachCase: SelecDocGiaMuonSachCase = SelecDocGiaMuonSachCase(),
        private val themFeildAddSachRongCase: ThemFeildAddSachRongCase = ThemFeildAddSachRongCase()
    ) : BaseViewModel() {


        val component = fetchCaseFields.result
        fun tryFetch() {
            if (fetchCaseFields.shouldFetch()) launch {
                fetchCaseFields()
            }
        }

        fun execute(it: Command) {
            when (it) {
                is SelecItemStartTextLayout -> launch(error) {
                    selectDocGiaMuonSachCase(
                        it.item,
                        component.value.orEmpty()
                    )
                }

                is AddFeildThemSachDangKi -> launch(error) {
                    themFeildAddSachRongCase.invoke(
                        component.value.orEmpty()
                    )
                }
            }
        }


    }
}

class ThemFeildAddSachRongCase {

    suspend operator fun invoke(list: List<IComponent>) {
        val mList = list as? ArrayList ?: return

    }

}

