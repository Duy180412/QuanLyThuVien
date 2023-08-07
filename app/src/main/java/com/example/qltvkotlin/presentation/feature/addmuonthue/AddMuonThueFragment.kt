package com.example.qltvkotlin.presentation.feature.addmuonthue

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding
import com.example.qltvkotlin.domain.enumeration.AddFeildThemSachDangKi
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.SelectDocGiaMuonSach
import com.example.qltvkotlin.domain.enumeration.SelectSachMuon
import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.helper.ActivityRetriever
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.HasValueKey
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.model.IInputLayoutField
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.domain.usecase.SelecThemDocGiaMuonSachCase
import com.example.qltvkotlin.domain.usecase.ThemFeildAddSachRongCase
import com.example.qltvkotlin.presentation.app.BaseFragmentNavigation
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.extension.checkStringNull
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewmodel
import com.example.qltvkotlin.presentation.helper.FetchAddMuonSachCaseFields
import com.example.qltvkotlin.presentation.widget.IItemSpinner
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter
import com.example.qltvkotlin.presentation.widget.fields.CustomManyFeilds
import com.example.qltvkotlin.presentation.widget.fields.SelectTextFeild


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
        private val themFeildAddSachRongCase: ThemFeildAddSachRongCase = ThemFeildAddSachRongCase(),
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

                is AddFeildThemSachDangKi -> launch(error) {
                    themFeildAddSachRongCase.invoke(
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
    private val activityRetriever: ActivityRetriever = ActivityRetriever.shared
) {
    suspend operator fun invoke(item: IFieldsCustom, component: List<IComponent>) {
        val selectTextFeild = item.getSelectFeild()
        val numberFeild = item.getNumberFeild()
        val sachDuocChon = dialogProvider.chonSach() ?: return
        val isExits = checkExits(sachDuocChon, component)
        if (isExits) throw Exception("Sách Này Đã Được Thêm")
        selectTextFeild.cast<Updatable>()?.update(sachDuocChon.nameKey)
        selectTextFeild.cast<HasValueKey>()?.key = sachDuocChon.key
        numberFeild.hint = activityRetriever().resources.getString(R.string.hintfeilds_maxint,sachDuocChon.status.checkStringNull())

    }

    private fun checkExits(sachDuocChon: IItemSpinner, list: List<IComponent>): Boolean {
        val listSach = mutableListOf<String>()
        list.forEach {
            when (it) {
                is CustomManyFeilds -> listSach.add(it.getSelectFeild().key.toString())
            }
        }
        listSach.forEach {
            if (it == sachDuocChon.key) return true
        }
        return false
    }
}

