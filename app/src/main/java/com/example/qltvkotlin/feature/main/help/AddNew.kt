package com.example.qltvkotlin.feature.main.help

import com.example.qltvkotlin.domain.model.IBookGet
import com.example.qltvkotlin.domain.model.IBookSet
import com.example.qltvkotlin.domain.model.IDocGiaGet
import com.example.qltvkotlin.domain.model.IDocGiaSet
import com.example.qltvkotlin.domain.model.MessageShowOwner
import com.example.qltvkotlin.domain.model.checkConditionChar
import com.example.qltvkotlin.domain.model.checkValueThrowError
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.domain.repo.SachRepo


class AddNewSach(val value: IBookSet) : MessageShowOwner {
    private val sachRepo = SachRepo.sachRepo
    suspend operator fun invoke() {
        val checkNull = checkConditionChar(value.tenSach, value.maSach)
        !checkNull checkValueThrowError message.charsEmpty
        val checkExit = sachRepo.checkSach(value.maSach.toString())
        checkExit checkValueThrowError value.maSach.toString() + message.isExist
        sachRepo.save(value)
    }

}


class AddNewDocGia(val value: IDocGiaSet) : MessageShowOwner {
    private val docGiaRepo = DocGiaRepo.docGiaRepo
    suspend operator fun invoke() {
        val checkNull = checkConditionChar(value.cmnd, value.tenDocGia,value.sdt,value.ngayHetHan)
        !checkNull checkValueThrowError message.charsEmpty
        val checkExit = docGiaRepo.checkDocGia(value.cmnd.toString())
        checkExit checkValueThrowError value.cmnd.toString() + message.isExist
        docGiaRepo.save(value)
    }
}