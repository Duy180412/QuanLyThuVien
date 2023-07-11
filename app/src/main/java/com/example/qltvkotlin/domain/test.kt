package com.example.qltvkotlin.domain

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.qltvkotlin.app.viewmodel

abstract class AbstractFragment() : Fragment() {
    abstract val viewmodel:Viewmodel1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.doSomThing()
    }


    abstract class Viewmodel1 : ViewModel() {
        fun doSomThing() {
        }
    }
}

abstract class AbstractFragment2 : AbstractFragment() {
    abstract override val viewmodel: Viewmodel2
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.doSomThing2()
    }

    abstract class Viewmodel2 : Viewmodel1() {
        fun doSomThing2() {
        }
    }
}

class FragmentChinh : AbstractFragment2() {
    override val viewmodel by viewmodel<ViewModleChinh>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.doSomThing3()
    }


    class ViewModleChinh : Viewmodel2() {
        fun doSomThing3() {
            TODO("Not yet implemented")
        }

    }
}




