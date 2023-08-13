package com.example.qltvkotlin.presentation.feature.addmuonthue

import android.os.Bundle
import android.view.View
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.FragmentRecyclerViewBinding
import com.example.qltvkotlin.domain.enumeration.AddFieldThemSachDangKi
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.RemoveField
import com.example.qltvkotlin.domain.enumeration.SelectDocGiaMuonSach
import com.example.qltvkotlin.domain.enumeration.SelectSachMuon
import com.example.qltvkotlin.domain.usecase.docgiadangkythue.ThemSachChoDocGiaThueCase
import com.example.qltvkotlin.domain.usecase.docgiadangkythue.HasChangeOnClickBackCase
import com.example.qltvkotlin.domain.usecase.docgiadangkythue.LuuDocGiaMuonSachMoiCase
import com.example.qltvkotlin.domain.usecase.docgiadangkythue.ThemDocGiaMuonSachCase
import com.example.qltvkotlin.domain.usecase.docgiadangkythue.ThemTruongRongDeNhapSachChoThueCase
import com.example.qltvkotlin.domain.usecase.docgiadangkythue.XoaTruongThemSachThueCuaDocGiaCase
import com.example.qltvkotlin.presentation.app.BaseFragmentNavigation
import com.example.qltvkotlin.presentation.extension.launch
import com.example.qltvkotlin.presentation.extension.viewBinding
import com.example.qltvkotlin.presentation.extension.viewModel
import com.example.qltvkotlin.presentation.helper.FetchAddMuonSachCaseFields
import com.example.qltvkotlin.presentation.widget.adapter.ComponentAdapter


class AddMuonThueFragment : BaseFragmentNavigation(R.layout.fragment_recycler_view) {
    private val binding by viewBinding { FragmentRecyclerViewBinding.bind(this) }
    override val viewModel by viewModel<VM>()
    override fun editOrSaveCase(it: View) {
        viewModel.save()
    }

    override fun getCheck(): () -> Boolean {
        return { viewModel.hasChange() }
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
        private val themDocGiaMuonSachCase: ThemDocGiaMuonSachCase = ThemDocGiaMuonSachCase(),
        private val themTruongRongDeNhapSachChoThueCase: ThemTruongRongDeNhapSachChoThueCase = ThemTruongRongDeNhapSachChoThueCase(),
        private val themSachChoDocGiaThueCase: ThemSachChoDocGiaThueCase = ThemSachChoDocGiaThueCase(),
        private val xoaTruongThemSachThueCuaDocGiaCase: XoaTruongThemSachThueCuaDocGiaCase = XoaTruongThemSachThueCuaDocGiaCase(),
        private val luuDocGiaMuonSachMoiCase: LuuDocGiaMuonSachMoiCase = LuuDocGiaMuonSachMoiCase(),
        private val hasChangeOnClickBackCase: HasChangeOnClickBackCase = HasChangeOnClickBackCase()
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
                    themDocGiaMuonSachCase(
                        it.item,
                        component.value.orEmpty()
                    )
                }

                is AddFieldThemSachDangKi -> launch(error) {
                    themTruongRongDeNhapSachChoThueCase.invoke(
                        component.value.orEmpty()
                    )
                }

                is SelectSachMuon -> launch(error) {
                    themSachChoDocGiaThueCase(it.item, component.value.orEmpty())
                }

                is RemoveField -> launch(error) {
                    xoaTruongThemSachThueCuaDocGiaCase(it.item, component.value.orEmpty())
                }
            }
        }

        fun save() {
            launch(error) {
                luuDocGiaMuonSachMoiCase.invoke(component.value.orEmpty())
            }
        }

        fun hasChange(): Boolean {
            return hasChangeOnClickBackCase(component.value.orEmpty())
        }


    }
}

