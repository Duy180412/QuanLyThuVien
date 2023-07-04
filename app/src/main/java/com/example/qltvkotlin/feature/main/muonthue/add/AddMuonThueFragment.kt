package com.example.qltvkotlin.feature.main.muonthue.add

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentNavigation
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewModel
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding
import com.example.qltvkotlin.datasource.bo.MuonSachEdittable
import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.domain.model.IMuonSachGet
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.domain.model.IThongTinSachThueCreate
import com.example.qltvkotlin.domain.model.ThongTinSachThueSet
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.domain.model.bindCharOwner
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.model.checkConditionChar
import com.example.qltvkotlin.feature.main.adapter.Command
import com.example.qltvkotlin.feature.main.adapter.DangKiMuonSachAdapter
import com.example.qltvkotlin.feature.main.adapter.ThemDocGiaCmd
import com.example.qltvkotlin.feature.main.adapter.ThemSachCmd
import com.example.qltvkotlin.feature.main.adapter.ThemThemSachRongCmd
import com.example.qltvkotlin.feature.main.adapter.XoaSachCmd
import com.example.qltvkotlin.feature.main.help.dialogcustom.DocGiaSelecDialogOnwer
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.show
import kotlinx.coroutines.launch


class AddMuonThueFragment : BaseFragmentNavigation(R.layout.fragment_add_muon_thue),
    DocGiaSelecDialogOnwer {
    private val binding by viewBinding { FragmentAddMuonThueBinding.bind(this) }
    private val viewmodel by viewModel<VM>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DangKiMuonSachAdapter(binding.rycView)

        adapter.onCommand = {
            viewmodel.execute(it)
        }

        binding.themdocgia.setStartIconOnClickListener {
            viewmodel.execute(ThemDocGiaCmd())
        }

        viewmodel.docGiaLive.observe(viewLifecycleOwner) {
            binding.themdocgianhap.setText(it)
        }

        viewmodel.newMuonThue.observe(viewLifecycleOwner) {
            val value = it.cast<IMuonSachSet>()!!
            adapter.setList(value.list)
            bindWhenOnChange(value)
        }
    }


    private fun bindWhenOnChange(value: IMuonSachSet) {
        value.maDocGia.also { it2 ->
            checkAndShowError(it2, binding.themdocgia)
            bindCharOwner(this, it2) {
                checkAndShowError(it, binding.themdocgia)
                show(binding.rycView, checkConditionChar(it))
            }
        }

    }

    override fun clickEditAndSave(it: View) {
        TODO("Not yet implemented")
    }

    override fun getCheck(): () -> Boolean {
        return { true }
    }

    class VM : ViewModel() {
        var newMuonThue = MutableLiveData<IMuonSach>()
        val dialogProvider = DialogProvider.shared
        val docGiaLive = MutableLiveData<String>()

        init {
            newMuonThue.value = MuonSachEdittable(object : IMuonSachGet {})
        }

        fun execute(it: Command) {
            when (it) {
                is ThemSachCmd -> themSach(it.sach)
                is ThemDocGiaCmd -> themDocGia()
                is XoaSachCmd -> xoaSach(it.sach)
                is ThemThemSachRongCmd -> themSachRong()
            }
        }

        private fun themSachRong() {
            val muonSach = newMuonThue.value.cast<IMuonSachSet>() ?: return
            muonSach.list.add(ThongTinSachThueSet())
        }

        private fun xoaSach(sach: IThongTinSachThueCreate) {
            val muonSach = newMuonThue.value.cast<IMuonSachSet>() ?: return
            val index = muonSach.list.indexOf(sach)
            muonSach.list.removeAt(index)
        }

        private fun themDocGia() {
            val muonSach = newMuonThue.value.cast<IMuonSachSet>() ?: return
            viewModelScope.launch {
                val docGia = dialogProvider.chonDocGia() ?: return@launch
                docGiaLive.value = docGia.nameKey
                muonSach.maDocGia.update(docGia.key)
            }
        }

        private fun themSach(thongTin: IThongTinSachThueCreate) {
            if (thongTin !is ThongTinSachThueSet) return

            viewModelScope.launch {
                val sachDuocChon = dialogProvider.chonSach() ?: return@launch
                thongTin.maSach.update(sachDuocChon.key)
                thongTin.tenSach.update(sachDuocChon.nameKey)
                thongTin.soLuong.setMax(sachDuocChon.status)
            }
        }
    }
}