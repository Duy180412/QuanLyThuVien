package com.example.qltvkotlin.feature.main.sach.addsach

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import com.example.qltvkotlin.domain.model.bindCharOwner
import com.example.qltvkotlin.domain.model.bindImageOwner
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.feature.action.TakePhotoActionOwner
import com.example.qltvkotlin.feature.main.help.AddNewSach
import com.example.qltvkotlin.feature.presentation.extension.bindTo
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.onClick


class AddSachFragment : BaseFragmentNavigation(R.layout.fragment_add_sach){

    private val binding by viewBinding { FragmentAddSachBinding.bind(this) }
    private val viewModel by viewModels<VM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextOnChange()
        takePhoto()
        viewModel.newBook.observe(viewLifecycleOwner) {
            bindWhenOnChange(it.cast<IBookSet>()!!)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            dialogFactory.notification(it.message!!)
        }
        viewModel.addSuccess.observe(viewLifecycleOwner) { closeFragment(it) }
    }

    override fun clickEditAndSave(it: View) {
        viewModel.saveSach()
    }


    override fun getCheck(): () -> Boolean {
        return  { viewModel.checkHasChange() }
    }

    private fun closeFragment(it: String) {
        toast.invoke(it)
        mActivity.finish()
    }


    private fun takePhoto() {
        binding.camera.onClick(
            appPermission.checkPermissonCamera(
                takePhotoAction.fromCamera { viewModel.setPhoto(it) })
        )
        binding.thuvien.onClick(takePhotoAction.fromLibrary {
            viewModel.setPhoto(it)
        })
    }

    private fun editTextOnChange() {
        val value = viewModel.newBook.value.cast<IBookSet>()!!
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
            bindCharOwner(this, it) {
                checkAndShowError(it, binding.masach)
            }
        }
        sach.tenSach.also { it ->
            checkAndShowError(it, binding.tensach)
            bindCharOwner(this, it) {
                checkAndShowError(it, binding.tensach)
            }
        }
        sach.imageSach.also { it ->
            bindImageOwner(this, it) {
                binding.avatar.setAvatar(it)
            }
        }

    }

    class VM : ViewModel() {
        val error = MutableLiveData<Throwable>()
        var newBook = MutableLiveData<ISach>()
        val addSuccess = MutableLiveData<String>()
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
                addSuccess.postValue("Đã Lưu Sách Thành Công")
            }
        }

        fun setPhoto(it: IsImageUri) {
            val value = this.newBook.value.cast<IBookSet>()
            value?.imageSach?.update(it)
        }
    }
}


