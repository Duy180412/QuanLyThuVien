package com.example.qltvkotlin.feature.main.sach.infosach

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentNavigation
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.databinding.FragmentInfoSachBinding
import com.example.qltvkotlin.datasource.bo.BookEditable
import com.example.qltvkotlin.domain.model.HasChange
import com.example.qltvkotlin.domain.model.IBookBackUp
import com.example.qltvkotlin.domain.model.IBookCreate
import com.example.qltvkotlin.domain.model.IBookGet
import com.example.qltvkotlin.domain.model.IBookSet
import com.example.qltvkotlin.domain.model.ISach
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.domain.model.bindCharOwner
import com.example.qltvkotlin.domain.model.bindImageOwner
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.model.checkConditionChar
import com.example.qltvkotlin.domain.repo.SachRepo
import com.example.qltvkotlin.feature.helper.lazyArgument
import com.example.qltvkotlin.feature.presentation.extension.bindTo
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.router.Routes


class InfoSachFragment : BaseFragmentNavigation(R.layout.fragment_info_sach) {
    private val binding by viewBinding { FragmentInfoSachBinding.bind(this) }
    private val viewModel by viewModels<VM>()
    private val args = lazyArgument<Routes.InfoSach>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setSach(args.value?.id)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        takePhoto()
        viewModel.book.observe(viewLifecycleOwner, this::bindView)
        viewModel.addSuccess.observe(viewLifecycleOwner) { toast.invoke(it) }
    }


    override fun getCheck(): () -> Boolean {
        return { viewModel.checkHasChange() }
    }

    private fun takePhoto() {
        binding.camera.onClick(
            appPermission.checkPermissonCamera(
                takePhotoAction.fromCamera { viewModel.setPhoto(it) })
        )
        binding.thuvien.onClick(takePhotoAction.fromLibrary { viewModel.setPhoto(it) })
    }


    override fun clickEditAndSave(it: View) {
        val iSach = viewModel.book.value
        if (iSach !is IBookSet) {
            viewModel.setSuaSach()
            it.isSelected = true
            return
        }
        if (!viewModel.checkHasChange()) {
            viewModel.tatSuaSach()
            it.isSelected = false
            return
        }
        if (viewModel.checkHasChange() || !viewModel.checkValidator()) {
            dialogFactory.selectYesNo("Sửa Sách ?",
                { viewModel.update() },
                { viewModel.tatSuaSach() })
        }
    }

    private fun bindView(iSach: ISach) {
        bind(iSach.cast<IBookCreate>()!!)
        setEditView(iSach is IBookSet)
        editTextOnChange(iSach.cast<IBookSet>())
        bindOnChange(iSach.cast<IBookSet>())
    }

    private fun bindOnChange(value: IBookSet?) {
        value ?: return
        value.tenSach.also { it ->
            checkAndShowError(it, binding.tensach)
            bindCharOwner(this, it) {
                checkAndShowError(it, binding.tensach)
            }
        }
        value.imageSach.also { it ->
            bindImageOwner(this, it) {
                binding.avatar.setAvatar(it)
            }
        }
    }

    private fun editTextOnChange(value: IBookSet?) {
        binding.tensachNhap.bindTo { value?.tenSach }
        binding.loaisachNhap.bindTo { value?.loaiSach }
        binding.tentacgiaNhap.bindTo { value?.tenTacGia }
        binding.namxbNhap.bindTo { value?.namXuatBan }
        binding.nxbNhap.bindTo { value?.nhaXuatBan }
        binding.tongNhap.bindTo { value?.tongSach }
    }

    private fun setEditView(value: Boolean) {
        binding.tensachNhap.isEnabled = value
        binding.loaisachNhap.isEnabled = value
        binding.tentacgiaNhap.isEnabled = value
        binding.namxbNhap.isEnabled = value
        binding.nxbNhap.isEnabled = value
        binding.tentacgiaNhap.isEnabled = value
        binding.tong.isEnabled = value
        binding.camera.isEnabled = value
        binding.thuvien.isEnabled = value
    }

    private fun bind(value: IBookCreate) {
        binding.avatar.setAvatar(value.imageSach)
        binding.tensachNhap.setText(value.tenSach)
        binding.loaisachNhap.setText(value.loaiSach)
        binding.tentacgiaNhap.setText(value.tenTacGia)
        binding.nxbNhap.setText(value.nhaXuatBan)
        binding.namxbNhap.setText(value.namXuatBan)
        binding.tongNhap.setText(value.tongSach)
        binding.dangChoThueNhap.setText(value.choThue)
    }


    class VM : ViewModel() {
        private val sachRepo = SachRepo.sachRepo
        val book = MutableLiveData<ISach>()
        val error = MutableLiveData<Throwable>()
        val addSuccess = MutableLiveData<String>()
        fun setSach(id: String?) {
            launch {
                book.postValue(sachRepo.getBookReadOnly(id))
            }
        }

        fun checkHasChange(): Boolean {
            book.value?.cast<IBookSet>() ?: return false
            return book.value?.cast<HasChange>()?.hasChange()!!
        }

        fun setSuaSach() {
            launch(error) {
                val bookRead = book.value.cast<IBookGet>()
                bookRead ?: throw Exception("Lỗi Hệ Thống")
                book.postValue(BookEditable(bookRead))
            }
        }

        fun tatSuaSach() {
            launch(error) {
                val bookEdit = book.value.cast<IBookBackUp>()
                bookEdit ?: throw Exception("Lỗi Hệ Thống")
                book.postValue(bookEdit.bookRead)
            }
        }

        fun checkValidator(): Boolean {
            var check = false
            launch(error) {
                val bookEdit = book.value.cast<IBookSet>()
                bookEdit ?: throw Exception("Lỗi Hệ Thống")
                check = checkConditionChar(bookEdit.tenSach)
            }
            return check
        }

        fun update() {
            launch {
                val bookEdit = book.value.cast<IBookSet>() ?: return@launch
                sachRepo.updateSach(bookEdit)
                addSuccess.postValue("Đã Lưu Sửa")
                setSach(bookEdit.maSach.toString())
            }
        }

        fun setPhoto(it: IsImageUri) {
            val value = this.book.value.cast<IBookSet>()
            value?.imageSach?.update(it)
        }
    }
}