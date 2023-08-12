package com.example.qltvkotlin.domain.usecase.docgia

import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.KhoiPhucDocGia
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.IDocGiaItem
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.presentation.extension.toIntOrZeroInt

class XoaDocGiaCase(
    private val docGiaRepo: DocGiaRepo = DocGiaRepo.shared,
    private val dialogProvider: DialogProvider = DialogProvider.shared
) {
    private fun dangThue(soLuongMuon: String): Boolean {
        return soLuongMuon.toIntOrZeroInt() > 0
    }

    suspend operator fun invoke(
        item: IDocGiaItem,
        list: List<IComponent>,
        khoiPhuc: (Command) -> Unit
    ) {
        val docGia = docGiaRepo.getInfoFullDocGiaDto(item.cmnd) ?: return
        if (dangThue(docGia.soLuongMuon)) throw Exception("Độc Giả Này Đang Mượn Sách")
        val index = list.indexOf(item)
        val mutableList = list as MutableList
        mutableList.removeAt(index)
        docGiaRepo.delDocGia(item.cmnd)
        dialogProvider.bottomUndo("Đã Xóa Độc Giả") {
            mutableList.add(index, item)
            khoiPhuc(KhoiPhucDocGia(docGia))
        }
    }
}