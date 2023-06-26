package com.example.qltvkotlin.feature.presentation.router


import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.feature.login.LoginActivity
import com.example.qltvkotlin.feature.main.MainActivity
import com.example.qltvkotlin.feature.main.docgia.add.AddDocGiaFragment
import com.example.qltvkotlin.feature.main.docgia.info.InfoDocGiaFragment
import com.example.qltvkotlin.feature.main.muonthue.add.AddMuonThueFragment
import com.example.qltvkotlin.feature.main.sach.addsach.AddSachFragment
import com.example.qltvkotlin.feature.main.sach.infosach.InfoSachFragment
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
    class InfoSach(val id: String) : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = InfoSachFragment::class
    }
    @Parcelize
    class AddDocGia : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = AddDocGiaFragment::class
    }

    @Parcelize
    class InfoDocGia(val id: String) : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = InfoDocGiaFragment::class
    }
    @Parcelize
    class AddMuon : FragmentRouting {
        override val clazzFragment: KClass<out Fragment>
            get() = AddMuonThueFragment::class
    }


}