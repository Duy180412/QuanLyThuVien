package com.example.qltvkotlin.domain.usecase.sach

import com.example.qltvkotlin.data.model.SachDTO
import com.example.qltvkotlin.domain.enumeration.Command
import com.example.qltvkotlin.domain.enumeration.KhoiPhucSach
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.model.ISachItem
import com.example.qltvkotlin.domain.repo.SachRepo
import com.example.qltvkotlin.presentation.extension.toIntOrZeroInt

class XoaSachCase(
    private val sachRepo: SachRepo = SachRepo.shared,
    private val dialogProvider: DialogProvider = DialogProvider.shared
) {
    suspend operator fun invoke(
        item: ISachItem,
        list: List<IComponent>,
        khoiPhuc: (Command) -> Unit
    ) {
        val sachDto = sachRepo.getSachById(item.maSach) ?: return
        if (daChoThue(sachDto)) throw Exception("Sách Này Đang Được Sử Dụng Không Thể Xóa")
        val index = list.indexOf(item)
        val mutableList = list as MutableList
        mutableList.removeAt(index)
        sachRepo.delSach(item.maSach)
        dialogProvider.bottomUndo("Đã Xóa Sách") {
            mutableList.add(index, item)
            khoiPhuc(KhoiPhucSach(sachDto))
        }

    }


    private fun daChoThue(sachDTO: SachDTO): Boolean {
        return sachDTO.choThue.toIntOrZeroInt() > 0
    }


}