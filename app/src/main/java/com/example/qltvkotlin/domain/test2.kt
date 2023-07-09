package com.example.qltvkotlin.domain

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


abstract class FragmentRecyclerView23 : Fragment() {
    lateinit var viewmodel: FragmentViewVM2 // Chưa được khởi tạo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.search("String")
    }

    abstract class FragmentViewVM2 : ViewModel() {
        abstract fun search(it: String)
    }
}

class FragmentB : FragmentRecyclerView23() {
    private var fragmentBViewModel: FragmentVM2 = ViewModelProvider(this)[FragmentVM2::class.java]

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = fragmentBViewModel // Khởi tạo viewmodel

        fragmentBViewModel.chathu()
    }

    class FragmentVM2 : FragmentViewVM2() {
        override fun search(it: String) {
            // Thực hiện tìm kiếm
            Log.d("FragmentB", "Tìm kiếm: $it")
        }

        fun chathu() {
            // Thực hiện công việc chathu
            search("Hello")
        }
    }
}