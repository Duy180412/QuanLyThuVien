package com.example.qltvkotlin.app

import androidx.appcompat.app.AppCompatActivity
import com.example.qltvkotlin.widget.view.ToastFactoryOwner
abstract class BaseActivity(contentLayoutid: Int) : AppCompatActivity(contentLayoutid),
    ToastFactoryOwner {
}