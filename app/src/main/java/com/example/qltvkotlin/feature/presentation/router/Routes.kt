package com.example.qltvkotlin.feature.presentation.router


import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.feature.login.LoginActivity
import com.example.qltvkotlin.feature.main.MainActivity
import com.example.qltvkotlin.feature.main.sach.AddSachFragment
import com.example.qltvkotlin.feature.main.sach.TestFragment
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass


object Routes  {
    @Parcelize
    class Main : ActivityRouting{
        override val clazzActivity: KClass<out Activity>
            get() = MainActivity::class
    }
    @Parcelize
    class Login : ActivityRouting{
        override val clazzActivity: KClass<out Activity>
            get() = LoginActivity::class
    }
    @Parcelize
    class AddSach: FragmentRouting{
        override val clazzFragment: KClass<out Fragment>
            get() = AddSachFragment::class
    }
    @Parcelize
    class Test: FragmentRouting{
        override val clazzFragment: KClass<out Fragment>
            get() = TestFragment::class
    }
}