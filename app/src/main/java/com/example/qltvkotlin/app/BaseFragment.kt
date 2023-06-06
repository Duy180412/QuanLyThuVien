package com.example.qltvkotlin.app

import androidx.fragment.app.Fragment
import com.example.qltvkotlin.feature.action.TakePhotoActionOwrn
import com.example.qltvkotlin.feature.presentation.app.AppPermissionOwer

abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId),
    AppPermissionOwer{
}