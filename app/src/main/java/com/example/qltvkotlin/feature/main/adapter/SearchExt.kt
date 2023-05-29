package com.example.qltvkotlin.feature.main.adapter

import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.datasource.roomdata.ThuVienDataRepo
import com.example.qltvkotlin.domain.model.IStringSearch
import com.example.qltvkotlin.domain.model.IsDocGiaSearch
import com.example.qltvkotlin.domain.model.IsSachSearch

class SearchExt {
    val thuVienDataRepo = ThuVienDataRepo.thuVienDataRepo
    var listSearch = MutableLiveData<List<Any>>()

    fun search(it: IStringSearch) {
        when (it) {
            is IsSachSearch -> listSearch.postValue(thuVienDataRepo.searchSach(it.mValueSach))
            is IsDocGiaSearch -> listSearch.postValue(thuVienDataRepo.searchDocGia(it.mValueDocGia))
        }
    }
 companion object{
     val searchExt = SearchExt()
 }

}