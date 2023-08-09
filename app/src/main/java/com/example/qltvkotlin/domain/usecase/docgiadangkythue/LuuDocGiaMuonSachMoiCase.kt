package com.example.qltvkotlin.domain.usecase.docgiadangkythue

import com.example.qltvkotlin.data.model.ThongTinThue
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.repo.MuonSachRepo
import com.example.qltvkotlin.presentation.widget.fields.CustomManyFields
import com.example.qltvkotlin.presentation.widget.fields.SelectTextField

class LuuDocGiaMuonSachMoiCase(
    private val muonSachRepo: MuonSachRepo = MuonSachRepo.shared
) {
    suspend operator fun invoke(list: List<IComponent>) {
        var cmndDocGiaDangKi: String = ""
        val isAllValid = list.all {
            val isValid = when (it) {
                is SelectTextField -> it.validate()
                is CustomManyFields -> it.getNumberField().validate() && it.getNumberField()
                    .validate()

                else -> true
            }
            isValid
        }
        if (!isAllValid) throw Exception("Có Trường Nhập Sai")
        val listSach = arrayListOf<ThongTinThue>()
        list.forEach {
            when (it) {
                is SelectTextField -> cmndDocGiaDangKi = it.key.toString()
                is CustomManyFields -> listSach.add(
                    ThongTinThue(
                        it.getSelectField().getValue(),
                        it.getNumberField().getValue().toInt()
                    )
                )
            }
        }
        val isExist = muonSachRepo.isExistsMuonSach(cmndDocGiaDangKi)
        if (isExist) throw Exception("Đã Có Độc Giả Này")
        muonSachRepo.save(cmndDocGiaDangKi, listSach)
    }

}