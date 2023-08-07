package com.example.qltvkotlin.presentation.feature.addmuonthue

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding
import com.example.qltvkotlin.domain.enumeration.AddFieldThemSachDangKi
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.SelectDocGiaMuonSach
import com.example.qltvkotlin.domain.enumeration.SelectSachMuon
import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.HasValueKey
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.usecase.SelecThemDocGiaMuonSachCase
import com.example.qltvkotlin.domain.usecase.ThemFieldAddSachRongCase
import com.example.qltvkotlin.presentation.app.BaseFragmentNavigation
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewmodel
import com.example.qltvkotlin.presentation.helper.AppStringResources
import com.example.qltvkotlin.presentation.helper.CharSequenceHint
import com.example.qltvkotlin.presentation.helper.FetchAddMuonSachCaseFields
import com.example.qltvkotlin.presentation.widget.IItemSpinner
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter
import com.example.qltvkotlin.presentation.widget.fields.CustomManyFields


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
        private val selectThemDocGiaMuonSachCase: SelecThemDocGiaMuonSachCase = SelecThemDocGiaMuonSachCase(),
        private val themFieldAddSachRongCase: ThemFieldAddSachRongCase = ThemFieldAddSachRongCase(),
        private val selctThemSachMuonCase: SelectThemSachMuonCase = SelectThemSachMuonCase()
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
                    selectThemDocGiaMuonSachCase(
                        it.item,
                        component.value.orEmpty()
                    )
                }

                is AddFieldThemSachDangKi -> launch(error) {
                    themFieldAddSachRongCase.invoke(
                        component.value.orEmpty()
                    )
                }

                is SelectSachMuon -> launch(error) {
                    selctThemSachMuonCase(it.item, component.value.orEmpty())
                }
            }
        }


    }
}

class SelectThemSachMuonCase(
    private val dialogProvider: DialogProvider = DialogProvider.shared,
    private val stringResources: AppStringResources = AppStringResources.shared
) {
    suspend operator fun invoke(item: IFieldsCustom, component: List<IComponent>) {
        val selectTextFeild = item.getSelectField()
        val numberField = item.getNumberField()
        val sachDuocChon = dialogProvider.chonSach() ?: return
        val isExits = checkExits(sachDuocChon, component)
        if (isExits) throw Exception("Sách Này Đã Được Thêm")
        selectTextFeild.cast<Updatable>()?.update(sachDuocChon.nameKey)
        selectTextFeild.cast<HasValueKey>()?.key = sachDuocChon.key
        numberField.iHint = CharSequenceHint(stringResources(StringId.MaxInt, sachDuocChon.status))
        numberField.setMax(sachDuocChon.status)
        numberField.enabled = true
        numberField.cast<Updatable>()?.update("0")

    }

    private fun checkExits(sachDuocChon: IItemSpinner, list: List<IComponent>): Boolean {
        val listSach = mutableListOf<String>()
        list.forEach {
            when (it) {
                is CustomManyFields -> listSach.add(it.getSelectField().key.toString())
            }
        }
        listSach.forEach {
            if (it == sachDuocChon.key) return true
        }
        return false
    }
}

