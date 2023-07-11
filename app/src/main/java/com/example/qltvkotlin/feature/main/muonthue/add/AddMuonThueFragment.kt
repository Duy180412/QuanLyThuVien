package com.example.qltvkotlin.feature.main.muonthue.add

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentNavigation
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.app.viewmodel
import com.example.qltvkotlin.databinding.FragmentAddMuonThueBinding
import com.example.qltvkotlin.datasource.bo.MuonSachEdittable
import com.example.qltvkotlin.domain.model.HasChange
import com.example.qltvkotlin.domain.model.IMuonSach
import com.example.qltvkotlin.domain.model.IMuonSachGeneral
import com.example.qltvkotlin.domain.model.IMuonSachGet
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.domain.model.IThongTinSachThueGeneral
import com.example.qltvkotlin.domain.model.ThongTinSachThueSet
import com.example.qltvkotlin.domain.model.bindOnChange
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.model.checkConditionChar
import com.example.qltvkotlin.feature.main.help.Command
import com.example.qltvkotlin.feature.main.adapter.DangKiMuonSachAdapter
import com.example.qltvkotlin.feature.main.help.ThemDocGiaCmd
import com.example.qltvkotlin.feature.main.help.ThemSachCmd
import com.example.qltvkotlin.feature.main.help.ThemThemSachRongCmd
import com.example.qltvkotlin.feature.main.help.XoaSachCmd
import com.example.qltvkotlin.feature.main.help.AddNewMuonSach
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.show


class AddMuonThueFragment : BaseFragmentNavigation(R.layout.fragment_add_muon_thue) {
    private val binding by viewBinding { FragmentAddMuonThueBinding.bind(this) }
    override val viewmodel by viewmodel<VM>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = DangKiMuonSachAdapter(binding.rycView)
        adapter.onCommand = {
            viewmodel.execute(it)
        }
        binding.themdocgia.setStartIconOnClickListener {
            viewmodel.execute(ThemDocGiaCmd())
        }
        viewmodel.newMuonSach.observe(viewLifecycleOwner) {
            val value = it.cast<IMuonSachGeneral>()!!
            adapter.setList(value.list)
            bindWhenOnChange(value)
        }
    }


    private fun bindWhenOnChange(value: IMuonSachGeneral) {
        value.tenDocGia.also { it ->
            bindOnChange(this, it) {
                binding.themdocgianhap.setText(it.toString())
            }
        }
        value.maDocGia.also { it2 ->
            checkAndShowError(it2, binding.themdocgia)
            bindOnChange(this, it2) {
                checkAndShowError(it, binding.themdocgia)
                show(binding.rycView, checkConditionChar(it))
            }
        }

    }

    override fun clickEditAndSave(it: View) {
        viewmodel.addMuonSach()
    }

    override fun getCheck(): () -> Boolean {
        return { viewmodel.checkHasChange() }
    }

    class VM : BaseViewModel() {
        var newMuonSach = MutableLiveData<IMuonSach>()
        private val dialogProvider = DialogProvider.shared

        init {
            newMuonSach.value = MuonSachEdittable(object : IMuonSachGet {})
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
            val muonSach = newMuonSach.value.cast<IMuonSachSet>() ?: return
            muonSach.list.add(ThongTinSachThueSet())
        }

        private fun xoaSach(sach: IThongTinSachThueGeneral) {
            val muonSach = newMuonSach.value.cast<IMuonSachSet>() ?: return
            val index = muonSach.list.indexOf(sach)
            muonSach.list.removeAt(index)
        }

        private fun themDocGia() {
            val muonSach = newMuonSach.value.cast<IMuonSachSet>() ?: return
            launch {
                val docGia = dialogProvider.chonDocGia() ?: return@launch
                muonSach.maDocGia.update(docGia.key)
                muonSach.tenDocGia.update(docGia.nameKey)
            }
        }

        private fun themSach(thongTin: IThongTinSachThueGeneral) {
            if (thongTin !is ThongTinSachThueSet) return
            launch {
                val sachDuocChon = dialogProvider.chonSach() ?: return@launch
                val checkExist =
                    newMuonSach.value.cast<IMuonSachSet>()?.list?.find { it.maSach.toString() == sachDuocChon.key } != null
                if (checkExist) {
                    thongBao.postValue("Sách Này Đã Được Thêm Vào")
                    return@launch
                }
                thongTin.maSach.update(sachDuocChon.key)
                thongTin.tenSach.update(sachDuocChon.nameKey)
                thongTin.soLuong.setMax(sachDuocChon.status)

            }

        }

        fun checkHasChange(): Boolean {
            return newMuonSach.value.cast<HasChange>()?.hasChange()!!
        }

        fun addMuonSach() {
            val newMuonSach = newMuonSach.value.cast<IMuonSachSet>() ?: return
            launch {
                val addNew = AddNewMuonSach(newMuonSach)
                addNew()
                successAndFinish.postValue("Thêm Mượn Sách")
            }
        }
    }
}