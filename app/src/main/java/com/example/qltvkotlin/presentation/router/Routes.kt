package com.example.qltvkotlin.presentation.router


import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.presentation.feature.addmuonthue.AddMuonThueFragment
import com.example.qltvkotlin.presentation.feature.addsach.AddSachFragment
import com.example.qltvkotlin.presentation.feature.login.LoginActivity
import com.example.qltvkotlin.presentation.feature.adddocgia.AddDocGiaFragment
import com.example.qltvkotlin.presentation.feature.infodocgia.InfoDocGiaFragment
import com.example.qltvkotlin.presentation.feature.infosach.InfoSachFragment
import com.example.qltvkotlin.presentation.feature.mainnavigato.MainActivity
import kotlinx.parcelize.Parcelize
import kotlin.reflect.KClass


object Routes {


    @Parcelize
    class Main : ActivityRouting {
        override val clazzActivity: KClass<out Activity>
            get() = MainActivity::class
    }

    @Parcelize
    class Login : ActivityRouting {
        override val clazzActivity: KClass<out Activity>
            get() = LoginActivity::class
    }

    @Parcelize
    class AddSach : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = AddSachFragment::class
    }

    @Parcelize
    class InfoSach(val id:CharSequence = "") : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = InfoSachFragment::class
    }
    @Parcelize
    class AddDocGia : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = AddDocGiaFragment::class
    }

    @Parcelize
    class InfoDocGia(val id: CharSequence = "") : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = InfoDocGiaFragment::class
    }
    @Parcelize
    class AddMuon : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = AddMuonThueFragment::class
    }


}