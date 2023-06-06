package com.example.qltvkotlin.feature.main.sach

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragment
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.databinding.FragmentAddSachBinding

import com.example.qltvkotlin.datasource.bo.SachEdit
import com.example.qltvkotlin.domain.model.HasChange

import com.example.qltvkotlin.domain.model.ISachEditable
import com.example.qltvkotlin.domain.model.ISachReadOnly
import com.example.qltvkotlin.domain.model.Images
import com.example.qltvkotlin.domain.model.IsImageEmpty

import com.example.qltvkotlin.domain.model.checkImageOwer
import com.example.qltvkotlin.domain.model.checkValidator
import com.example.qltvkotlin.domain.model.checkValidatorOwer
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.feature.action.TakePhotoActionOwrn
import com.example.qltvkotlin.feature.helper.OnBackClick
import com.example.qltvkotlin.feature.main.mainnavigato.MainNavigationActivity
import com.example.qltvkotlin.feature.presentation.extension.bindTo
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.widget.view.diaLogSelect
import android.os.Bundle as Bundle1


class AddSachFragment : BaseFragment(R.layout.fragment_add_sach), Signal.Closable by Signal.Bags(),
    TakePhotoActionOwrn {

    private val binding by viewBinding { FragmentAddSachBinding.bind(this) }
    private val viewModel by viewModels<VM>()
    private lateinit var mActivity: MainNavigationActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle1?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = requireActivity() as MainNavigationActivity
        val value = viewModel.sachMoi.value!!
        val checkChange = value.cast<HasChange>()?.hasChange()!!
        val onBackClick = OnBackClick(requireContext(),checkChange){
            diaLogSelect(requireContext(), "Hủy Thêm", { mActivity.finish() }, {})
        }


        editTextOnChanger(value)
        takePhoto(value)
        viewModel.sachMoi.observe(viewLifecycleOwner, this::bindWhenOnChange)
        mActivity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackClick)
        mActivity.actionBarView.onClickBack = { onBackClick.function.invoke() }
    }

    private fun eventOnBack(value: ISachEditable): () -> Unit {
        return {
            val checkChange = value.cast<HasChange>()?.hasChange()!!
            if (checkChange) {
                diaLogSelect(requireContext(), "Hủy Thêm", { mActivity.finish() }, {})
            } else {
                mActivity.finish()
            }
        }
    }


    private fun takePhoto(value: ISachEditable) {
        binding.camera.onClick(
            appPermission.checkPermissonCamera(
                takePhotoAction.fromCamera {
                    value.imageSach.update(it)
                })
        )
        binding.thuvien.onClick(takePhotoAction.fromLibrary {
            value.imageSach.update(it)
        })
    }

    private fun editTextOnChanger(value: ISachEditable) {
        binding.masachNhap.bindTo { value.maSach }
        binding.tensachNhap.bindTo { value.tenSach }
        binding.loaisachNhap.bindTo { value.loaiSach }
        binding.tentacgiaNhap.bindTo { value.tenTacGia }
        binding.namxbNhap.bindTo { value.namXuatBan }
        binding.nxbNhap.bindTo { value.nhaXuatBan }
        binding.tongNhap.bindTo { value.tongSach }
    }


    private fun bindWhenOnChange(sach: ISachEditable) {
        binding.avatar.setAvatar(sach.imageSach)
        sach.maSach.also { it ->
            checkValidator(it, binding.masach)
            checkValidatorOwer(this, it) {
                checkValidator(it, binding.masach)
            }
        }
        sach.tenSach.also { it ->
            checkValidator(it, binding.tensach)
            checkValidatorOwer(this, it) {
                checkValidator(it, binding.tensach)
            }
        }
        sach.imageSach.also { it ->
            checkImageOwer(this, it) {
                binding.avatar.setAvatar(it)
            }
        }

    }

    class VM : ViewModel() {
        var sachMoi = MutableLiveData<ISachEditable>()

        init {
            sachMoi.value = SachEdit(object : ISachReadOnly {
                override val maSach = ""
                override val imageSach = Images(object : IsImageEmpty {})
                override val tenSach = ""
                override val loaiSach = ""
                override val tenTacGia = ""
                override val nhaXuatBan = ""
                override val namXuatBan = ""
                override val tongSach = ""
                override val choThue = ""
            })
        }
    }

}
