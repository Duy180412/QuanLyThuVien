package com.example.qltvkotlin.presentation.feature.addmuonthue

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding
import com.example.qltvkotlin.domain.enumeration.AddFeildThemSachDangKi
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.SelectDocGiaMuonSach
import com.example.qltvkotlin.domain.enumeration.StringIds
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.usecase.SelecDocGiaMuonSachCase
import com.example.qltvkotlin.presentation.app.BaseFragmentNavigation
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewmodel
import com.example.qltvkotlin.presentation.helper.FetchAddMuonSachCaseFields
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter
import com.example.qltvkotlin.presentation.widget.fields.CustomManyFeilds


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
                is SelectDocGiaMuonSach -> launch(error) {
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
    operator fun invoke(list: List<IComponent>) {
        val mList = list as? MutableList ?: return
        mList.add(mList.size - 1, CustomManyFeilds(StringIds.AddSachMuon))
        mList.cast<Signal>()?.emit()
    }

}

