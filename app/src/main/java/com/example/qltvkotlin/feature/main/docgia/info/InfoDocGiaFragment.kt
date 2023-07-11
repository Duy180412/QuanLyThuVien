package com.example.qltvkotlin.feature.main.docgia.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.R
import com.example.qltvkotlin.app.BaseFragmentNavigation
import com.example.qltvkotlin.app.launch
import com.example.qltvkotlin.app.viewBinding
import com.example.qltvkotlin.databinding.FragmentInfoDocGiaBinding
import com.example.qltvkotlin.datasource.bo.DocGiaEditable
import com.example.qltvkotlin.domain.model.HasChange
import com.example.qltvkotlin.domain.model.IDocGia
import com.example.qltvkotlin.domain.model.IDocGiaBackUp
import com.example.qltvkotlin.domain.model.IDocGiaCreate
import com.example.qltvkotlin.domain.model.IDocGiaGet
import com.example.qltvkotlin.domain.model.IDocGiaSet
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.domain.model.bindOnChange
import com.example.qltvkotlin.domain.model.checkAndShowError
import com.example.qltvkotlin.domain.model.checkConditionChar
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.feature.helper.lazyArgument
import com.example.qltvkotlin.feature.presentation.extension.bindTo
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.router.Routes
import com.example.qltvkotlin.widget.view.DateOnClick

class InfoDocGiaFragment : BaseFragmentNavigation(R.layout.fragment_info_doc_gia) {
    private val binding by viewBinding { FragmentInfoDocGiaBinding.bind(this) }
    override val viewmodel by viewModels<VM>()
    private val args = lazyArgument<Routes.InfoDocGia>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.setDocGia(args.value?.id)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        takePhoto()
        viewmodel.docGia.observe(viewLifecycleOwner, this::bindView)
    }


    override fun getCheck(): () -> Boolean {
        return { viewmodel.checkHasChange() }
    }

    private fun takePhoto() {
        binding.camera.onClick(
            appPermission.checkPermissonCamera(takePhotoAction.fromCamera { viewmodel.setPhoto(it) })
        )
        binding.thuvien.onClick(takePhotoAction.fromLibrary { viewmodel.setPhoto(it) })
    }

    override fun clickEditAndSave(it: View) {
        val iSach = viewmodel.docGia.value
        if (iSach !is IDocGiaSet) {
            viewmodel.setSuaDocGia()
            it.isSelected = true
            return
        }
        if (!viewmodel.checkHasChange()) {
            viewmodel.tatSuaDocGia()
            it.isSelected = false
            return
        }
        if (viewmodel.checkHasChange() || !viewmodel.checkValidator()) {
            dialog.selectYesNo("Sửa Sách ?",
                { viewmodel.update() },
                { viewmodel.tatSuaDocGia() })
        }
    }

    private fun bindView(iDocGia: IDocGia) {
        bind(iDocGia.cast<IDocGiaCreate>()!!)
        setEditView(iDocGia is IDocGiaSet)
        editTextOnChange(iDocGia.cast<IDocGiaSet>())
        bindOnChange(iDocGia.cast<IDocGiaSet>())
    }

    private fun bindOnChange(value: IDocGiaSet?) {
        value ?: return
        value.tenDocGia.also { it ->
            checkAndShowError(it, binding.tendocgia)
            bindOnChange(this, it) {
                checkAndShowError(it, binding.tendocgia)
            }
        }
        value.images.also { it ->
            bindOnChange(this, it) {
                binding.avatar.setAvatar(it)
            }
        }
        binding.ngayhethanNhap.onClick(DateOnClick(value.ngayHetHan))
        value.ngayHetHan.also { it ->
            checkAndShowError(it, binding.ngayhethan)
            bindOnChange(this, it) {
                checkAndShowError(it, binding.ngayhethan)
            }
        }
        value.sdt.also { it ->
            checkAndShowError(it, binding.sdt)
            bindOnChange(this, it) {
                checkAndShowError(it, binding.sdt)
            }
        }

    }

    private fun editTextOnChange(value: IDocGiaSet?) {
        binding.cmndNhap.bindTo { value?.cmnd }
        binding.tendocgiaNhap.bindTo { value?.tenDocGia }
        binding.ngayhethanNhap.bindTo { value?.ngayHetHan }
        binding.sdtNhap.bindTo { value?.sdt }
        binding.soluongmuonNhap.bindTo { value?.soLuongMuon }
    }

    private fun setEditView(value: Boolean) {
        binding.tendocgiaNhap.isEnabled = value
        binding.ngayhethanNhap.isEnabled = value
        binding.sdtNhap.isEnabled = value
        binding.camera.isEnabled = value
        binding.thuvien.isEnabled = value
    }

    private fun bind(value: IDocGiaCreate) {
        binding.avatar.setAvatar(value.images)
        binding.tendocgiaNhap.setText(value.tenDocGia.toString())
        binding.ngayhethanNhap.setText(value.ngayHetHan.toString())
        binding.sdtNhap.setText(value.sdt.toString())

    }


    class VM : BaseViewModel() {
        private val docGiaRepo = DocGiaRepo.shared
        val docGia = MutableLiveData<IDocGia>()
        fun setDocGia(id: String?) {
            launch(error) {
                docGia.postValue(docGiaRepo.getDocGiaReadOnly(id))
            }
        }

        fun checkHasChange(): Boolean {
            docGia.value?.cast<IDocGiaSet>() ?: return false
            return docGia.value?.cast<HasChange>()?.hasChange()!!
        }

        fun setSuaDocGia() {
            launch(error) {
                val docGiaRead = docGia.value.cast<IDocGiaGet>()
                docGiaRead ?: throw Exception("Lỗi Hệ Thống")
                docGia.postValue(DocGiaEditable(docGiaRead))
            }
        }

        fun tatSuaDocGia() {
            launch(error) {
                val docGiaEdit = docGia.value.cast<IDocGiaBackUp>()
                docGiaEdit ?: throw Exception("Lỗi Hệ Thống")
                docGia.postValue(docGiaEdit.docGiaRead)
            }
        }

        fun checkValidator(): Boolean {
            var check = false
            launch {
                val docGiaEdit = docGia.value.cast<IDocGiaSet>()
                docGiaEdit ?: throw Exception("Lỗi Hệ Thống")
                check =
                    checkConditionChar(docGiaEdit.tenDocGia, docGiaEdit.sdt, docGiaEdit.ngayHetHan)
            }
            return check
        }

        fun update() {
            launch(error) {
                val docGiaEdit = docGia.value.cast<IDocGiaSet>() ?: return@launch
                docGiaRepo.update(docGiaEdit)
                thongBao.postValue("Đã Lưu Sửa")
                setDocGia(docGiaEdit.cmnd.toString())
            }
        }

        fun setPhoto(it: IsImageUri) {
            val value = this.docGia.value.cast<IDocGiaSet>()
            value?.images?.update(it)
        }

    }
}