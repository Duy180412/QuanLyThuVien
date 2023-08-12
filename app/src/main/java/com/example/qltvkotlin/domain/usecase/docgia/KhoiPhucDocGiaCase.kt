package com.example.qltvkotlin.domain.usecase.docgia

import com.example.qltvkotlin.domain.enumeration.KhoiPhucDocGia
import com.example.qltvkotlin.domain.repo.DocGiaRepo

class KhoiPhucDocGiaCase(private val docGiaRepo: DocGiaRepo = DocGiaRepo.shared) {
    suspend operator fun invoke(item: KhoiPhucDocGia) {
        docGiaRepo.addDocGia(item.docGiaDTO)
    }
}