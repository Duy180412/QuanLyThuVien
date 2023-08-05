package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.presentation.extension.cast
import com.google.android.material.textfield.TextInputLayout


fun checkAndShowError(it: CharSequence, textLayout: TextInputLayout) {
    val value = it.cast<Validable>()?.validate()!!
    textLayout.isErrorEnabled = !value
    val error = if (!value) it.cast<HasError>()?.getError() else return
    textLayout.error = error
}
enum class MessageId(val value: String) {
    errorSystem(" Lỗi hệ thống"),
    charsEmpty(" Không thể để trống các mục bắt buộc"),
    isExist(" Đã tồn tại "),
    wrongFormat(" Không chấp nhận kiểu giá trị này"),
    saveHasImage(" Đã lưu có hình ảnh"),
    saveNoImage(" Đã lưu không có hình ảnh"),
    delSuccess(" Đã Xóa Thành Công "),
    delFailure(" Không Thể Xóa "),
    delSuccessMain(" Đã Xóa Thành Công Ở Danh Sách Chính "),
    delFailureMain(" Không Thể Xóa Ở Danh Sách Chính "),
    undoSuccess("Đã Hoàn Tác "),
    undoFailure("Không Thể Hoàn Tác "),
}
