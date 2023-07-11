package com.example.qltvkotlin.feature.main.sach.addsach

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentNavigation
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.databinding.FragmentAddSachBinding
import com.example.qltvkotlin.datasource.bo.BookEditable
import com.example.qltvkotlin.domain.model.HasChange
import com.example.qltvkotlin.domain.model.IBookGet
import com.example.qltvkotlin.domain.model.IBookSet
import com.example.qltvkotlin.domain.model.ISach
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.domain.model.bindOnChange
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.feature.main.help.AddNewSach
import com.example.qltvkotlin.feature.presentation.extension.bindTo
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.onClick


class AddSachFragment : BaseFragmentNavigation(R.layout.fragment_add_sach){

    private val binding by viewBinding { FragmentAddSachBinding.bind(this) }
    override val viewmodel by viewModels<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextOnChange()
        takePhoto()
        viewmodel.newBook.observe(viewLifecycleOwner) {
            bindWhenOnChange(it.cast<IBookSet>()!!)
        }
    }

    override fun clickEditAndSave(it: View) {
        viewmodel.saveSach()
    }


    override fun getCheck(): () -> Boolean {
        return  { viewmodel.checkHasChange() }
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
        val value = viewmodel.newBook.value.cast<IBookSet>()!!
        binding.masachNhap.bindTo { value.maSach }
        binding.tensachNhap.bindTo { value.tenSach }
        binding.loaisachNhap.bindTo { value.loaiSach }
        binding.tentacgiaNhap.bindTo { value.tenTacGia }
        binding.namxbNhap.bindTo { value.namXuatBan }
        binding.nxbNhap.bindTo { value.nhaXuatBan }
        binding.tongNhap.bindTo { value.tongSach }
    }


    private fun bindWhenOnChange(sach: IBookSet) {
        binding.avatar.setAvatar(sach.imageSach)
        sach.maSach.also { it ->
            checkAndShowError(it, binding.masach)
            bindOnChange(this, it) {
                checkAndShowError(it, binding.masach)
            }
        }
        sach.tenSach.also { it ->
            checkAndShowError(it, binding.tensach)
            bindOnChange(this, it) {
                checkAndShowError(it, binding.tensach)
            }
        }
        sach.imageSach.also { it ->
            bindOnChange(this, it) {
                binding.avatar.setAvatar(it)
            }
        }

    }

    class VM : BaseViewModel() {
        var newBook = MutableLiveData<ISach>()
        fun checkHasChange(): Boolean {
            return newBook.value.cast<HasChange>()?.hasChange()!!
        }

        init {
            newBook.value = BookEditable(object :IBookGet{})
        }

        fun saveSach() {
            val value = newBook.value.cast<IBookSet>() ?: return
            launch(error) {
                val addNewSach = AddNewSach(value)
                addNewSach()
                successAndFinish.postValue("Đã Lưu Sách Thành Công")
            }
        }

        fun setPhoto(it: IsImageUri) {
            val value = this.newBook.value.cast<IBookSet>()
            value?.imageSach?.update(it)
        }
    }
}


