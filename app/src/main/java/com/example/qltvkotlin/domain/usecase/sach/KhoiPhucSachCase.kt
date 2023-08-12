package com.example.qltvkotlin.domain.usecase.sach

import com.example.qltvkotlin.domain.enumeration.KhoiPhucSach
import com.example.qltvkotlin.domain.repo.SachRepo

class KhoiPhucSachCase(private val sachRepo: SachRepo = SachRepo.shared) {
    suspend operator fun invoke(command: KhoiPhucSach) {
        val sachDto = command.sachDto
        sachRepo.addSach(sachDto)
    }
}