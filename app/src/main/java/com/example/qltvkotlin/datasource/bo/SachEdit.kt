package com.example.qltvkotlin.datasource.bo

import androidx.lifecycle.MutableLiveData
import com.example.qltvkotlin.domain.model.Chars
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.ISachEdit

class SachEdit {
    var sachEdit = MutableLiveData<ISachEdit>()

    init {
        sachEdit.postValue(
            object :ISachEdit {
            override var maSach = Chars("")
            override var imageSach = object :IImage{}
            override var tenSach = Chars("")
            override var loaiSach = ""
            override var tenTacGia = ""
            override var nhaXuatBan = ""
            override var namXuatBan = ""
            override var tongSach = ""
            override var choThue = ""
        })
    }
    companion object{
        val instance = SachEdit()
    }
}