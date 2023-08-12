package com.example.qltvkotlin.domain.usecase

import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.domain.enumeration.TypeSearch
import com.example.qltvkotlin.domain.model.IBackUpFieldRemove
import com.example.qltvkotlin.domain.model.IComponent
import com.example.qltvkotlin.domain.observable.Signal
import com.example.qltvkotlin.domain.observable.signal
import com.example.qltvkotlin.domain.repo.DocGiaRepo
import com.example.qltvkotlin.domain.repo.MuonSachRepo
import com.example.qltvkotlin.domain.repo.SachRepo
import com.example.qltvkotlin.presentation.extension.post

class SearchCase(
    private val sachRepo: SachRepo = SachRepo.shared,
    private val docGiaRepo: DocGiaRepo = DocGiaRepo.shared,
    private val muonSachRepo: MuonSachRepo = MuonSachRepo.shared
) {
    var result = MutableLiveData<List<IComponent>>()
    private var keySearch: String = ""

    suspend operator fun invoke(typeSearch: TypeSearch, it: String = "") {
        keySearch = it
        when (typeSearch) {
            TypeSearch.Sach -> setListSach()
            TypeSearch.DocGia -> setListDocGia()
            TypeSearch.ConHan -> setListDangThue()
            TypeSearch.HetHan -> setListHetHan()
        }
    }

    private suspend fun setListHetHan() {
        val instance = muonSachRepo.searchHetHan(keySearch).toMutableList<IComponent>()
        result.post(toMutableListCustom(instance))
    }

    private suspend fun setListDangThue() {
        val instance = muonSachRepo.searchDangThue(keySearch).toMutableList<IComponent>()
        result.post(toMutableListCustom(instance))
    }

    private suspend fun setListDocGia() {
        val instance = docGiaRepo.searchDocGia(keySearch).toMutableList<IComponent>()
        result.post(toMutableListCustom(instance))
    }

    private suspend fun setListSach() {
        val instance = sachRepo.searchSach(keySearch).toMutableList<IComponent>()
        result.post(toMutableListCustom(instance))
    }
}


fun toMutableListCustom(instance: MutableList<IComponent>): MutableList<IComponent> {
    return object : MutableList<IComponent> by instance, Signal by signal(),
        IBackUpFieldRemove {
        override fun add(index: Int, element: IComponent) {
            return instance.add(index, element).also { emit() }
        }

        override fun removeAt(index: Int): IComponent {
            return instance.removeAt(index).also { emit() }
        }

        override val fieldRemove: List<IComponent> = emptyList()
    }
}