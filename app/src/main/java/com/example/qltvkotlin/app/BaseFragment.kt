package com.example.qltvkotlin.app

import androidx.fragment.app.Fragment
import com.example.qltvkotlin.domain.model.MessageShowOwner
import com.example.qltvkotlin.feature.presentation.app.AppPermissionOwer
import com.example.qltvkotlin.widget.view.DialogFactoryOwner
import com.example.qltvkotlin.widget.view.ToastFactoryOwner

abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId),
    AppPermissionOwer,
    DialogFactoryOwner, ToastFactoryOwner, MessageShowOwner {}