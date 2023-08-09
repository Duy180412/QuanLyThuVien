package com.example.qltvkotlin.domain.usecase.docgiadangkythue

import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IFieldsCustom
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.presentation.extension.cast

class XoaTruongThemSachThueCuaDocGiaCase {
    operator fun invoke(item: IFieldsCustom, list: List<IComponent>) {
        val index = list.indexOf(item)
        if (index < 0) throw Exception("Không Tìm Thấy Trường")
        if (list !is MutableList) throw Exception("List này không thể sửa")
        list.removeAt(index)
        list.cast<Signal>()?.emit()
        // Hủy bỏ lưu và khôi phục trường không cần thiết tính năng này sẽ được thêm vào sau
//        val listBackUp = list.cast<IBackUpFieldRemove>()?.fieldRemove as? MutableList
//        listBackUp ?: throw Exception("Không tìm thấy BackUp")
//        listBackUp.add(item)
    }
}