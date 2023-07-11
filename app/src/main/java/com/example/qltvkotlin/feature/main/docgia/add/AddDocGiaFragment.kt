package com.example.qltvkotlin.feature.main.docgia.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentNavigation
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.databinding.FragmentAddDocGiaBinding
import com.example.qltvkotlin.datasource.bo.DocGiaEditable
import com.example.qltvkotlin.domain.model.HasChange
import com.example.qltvkotlin.domain.model.IDocGia
import com.example.qltvkotlin.domain.model.IDocGiaGet
import com.example.qltvkotlin.domain.model.IDocGiaSet
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.domain.model.bindOnChange
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.feature.main.help.AddNewDocGia
import com.example.qltvkotlin.feature.presentation.extension.bindTo
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.widget.view.DateOnClick

class AddDocGiaFragment : BaseFragmentNavigation(R.layout.fragment_add_doc_gia) {

    private val binding by viewBinding { FragmentAddDocGiaBinding.bind(this) }
    override val viewmodel by viewModels<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextOnChange()
        takePhoto()
        viewmodel.newDocGia.observe(viewLifecycleOwner) {
            bindWhenOnChange(it.cast<IDocGiaSet>()!!)
        }
    }


    override fun clickEditAndSave(it: View) {
        viewmodel.saveDocGia()
    }


    override fun getCheck(): () -> Boolean {
        return { viewmodel.checkHasChange() }
    }


    private fun takePhoto() {
        binding.camera.onClick(
            appPermission.checkPermissonCamera(
                takePhotoAction.fromCamera { viewmodel.setPhoto(it) })
        )
        binding.thuvien.onClick(takePhotoAction.fromLibrary {
            viewmodel.setPhoto(it)
        })
    }

    private fun editTextOnChange() {
        val value = viewmodel.newDocGia.value.cast<IDocGiaSet>()!!
        binding.cmndNhap.bindTo { value.cmnd }
        binding.tendocgiaNhap.bindTo { value.tenDocGia }
        binding.ngayhethanNhap.bindTo { value.ngayHetHan }
        binding.sdtNhap.bindTo { value.sdt }
        binding.soluongmuonNhap.bindTo { value.soLuongMuon}
        binding.ngayhethanNhap.onClick(DateOnClick(value.ngayHetHan))
    }


    private fun bindWhenOnChange(docGia: IDocGiaSet) {
        binding.avatar.setAvatar(docGia.images)
        docGia.cmnd.also { it ->
            checkAndShowError(it, binding.cmnd)
            bindOnChange(this, it) {
                checkAndShowError(it, binding.cmnd)
            }
        }
        docGia.tenDocGia.also { it ->
            checkAndShowError(it, binding.tendocgia)
            bindOnChange(this, it) {
                checkAndShowError(it, binding.tendocgia)
            }
        }

        docGia.ngayHetHan.also { it->
            checkAndShowError(it, binding.ngayhethan)
            bindOnChange(this,it){
                checkAndShowError(it, binding.ngayhethan)
            }
        }
        docGia.sdt.also {it ->
            checkAndShowError(it,binding.sdt)
            bindOnChange(this,it){
                checkAndShowError(it,binding.sdt)
            }
        }
        docGia.images.also { it ->
            bindOnChange(this, it) {
                binding.avatar.setAvatar(it)
            }
        }


    }
    class VM : BaseViewModel() {
        var newDocGia = MutableLiveData<IDocGia>()
        fun checkHasChange(): Boolean {
            return newDocGia.value.cast<HasChange>()?.hasChange()!!
        }

        init {
            newDocGia.value = DocGiaEditable(object :IDocGiaGet{})
        }

        fun saveDocGia() {
            val value = newDocGia.value.cast<IDocGiaSet>() ?: return
            launch(error) {
                val addNewDocGia = AddNewDocGia(value)
                addNewDocGia()
                successAndFinish.postValue("Đã Lưu Đọc Giả Thành Công")
            }
        }

        fun setPhoto(it: IsImageUri) {
            val value = this.newDocGia.value.cast<IDocGiaSet>()
            value?.images?.update(it)
        }
    }
}

