package com.example.qltvkotlin.feature.main.adapter

import androidx.lifecycle.MutableLiveData

import com.example.qltvkotlin.domain.model.IStringSearch
import com.example.qltvkotlin.domain.model.IsSachSearch
import com.example.qltvkotlin.feature.presentation.extension.pairLookupOf

class SearchExt() {
    var list:List<Any> = emptyList()
    var mListOriginal = MutableLiveData<List<Any>>()
    private var stringSearch: IStringSearch = TODO()


    fun search(it: IStringSearch) {
        setListOriginal(it)
        stringSearch = it
        doSearch()
    }

    private fun setListOriginal(it: IStringSearch) {
        when(it){
            is IsSachSearch ->
        }

    }

    private fun doSearch() {
    }
}