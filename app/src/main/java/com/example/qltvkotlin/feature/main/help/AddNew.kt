package com.example.qltvkotlin.feature.main.help

import com.example.qltvkotlin.domain.model.IBookSet
import com.example.qltvkotlin.domain.model.IDocGiaSet
import com.example.qltvkotlin.domain.model.IMuonSachSet
import com.example.qltvkotlin.domain.model.MessageShowOwner
import com.example.qltvkotlin.domain.model.checkConditionChar
import com.example.qltvkotlin.domain.model.checkValueThrowError
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.domain.repo.MuonThueRepo
import com.example.qltvkotlin.domain.repo.SachRepo

class AddNewMuonSach(val value: IMuonSachSet) : MessageShowOwner {
    private val instance = MuonThueRepo.shared
    suspend operator fun invoke() {
        val checkListNull = value.list.isNotEmpty()
        val listCheck = value.list.flatMap { listOf(it.maSach, it.soLuong) }.toTypedArray()
        val checkError = checkConditionChar(value.maDocGia, *listCheck) || checkListNull
        !checkError checkValueThrowError message.charsEmpty
        val checkExist = instance.checkMuonSach(value.maDocGia.toString())
        checkExist checkValueThrowError value.tenDocGia.toString() + message.isExist
        instance.save(value)
    }
}

class AddNewSach(val value: IBookSet) : MessageShowOwner {
    private val instance = SachRepo.shared
    suspend operator fun invoke() {
        val checkError = checkConditionChar(value.tenSach, value.maSach)
        !checkError checkValueThrowError message.charsEmpty
        val checkExist = instance.checkSach(value.maSach.toString())
        checkExist checkValueThrowError value.maSach.toString() + message.isExist
        instance.save(value)
    }

}


class AddNewDocGia(val value: IDocGiaSet) : MessageShowOwner {
    private val instance = DocGiaRepo.shared
    suspend operator fun invoke() {
        val checkError =
            checkConditionChar(value.cmnd, value.tenDocGia, value.sdt, value.ngayHetHan)
        !checkError checkValueThrowError message.charsEmpty
        val checkExist = instance.checkDocGia(value.cmnd.toString())
        checkExist checkValueThrowError value.cmnd.toString() + message.isExist
        instance.save(value)
    }
}