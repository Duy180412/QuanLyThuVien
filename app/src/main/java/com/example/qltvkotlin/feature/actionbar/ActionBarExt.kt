package com.example.qltvkotlin.feature.actionbar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.viewbinding.ViewBinding
import com.example.qltvkotlin.domain.model.IsDocGiaSearch
import com.example.qltvkotlin.domain.model.IsSachSearch
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.TieuDeTimkiemBinding
import com.example.qltvkotlin.databinding.TimkiemBinding
import com.example.qltvkotlin.domain.model.IStringSearch
import com.example.qltvkotlin.feature.main.docgia.DocGiaFragment
import com.example.qltvkotlin.feature.main.sach.SachFragment
import com.example.qltvkotlin.feature.presentation.extension.onClick
import com.example.qltvkotlin.feature.presentation.extension.pairLookupOf
import com.example.qltvkotlin.feature.presentation.extension.show
import kotlin.reflect.KClass

interface ActionBarOwer {
    val actionBarExt: ActionBarExt
        get() {
            if (this !is Fragment) error("Lỗi Fragment")
            var current = parentFragment
            while (current != null) {
                if (current is ActionBarOwer) return current.actionBarExt
                current = current.parentFragment
            }
            error("Lỗi Actionbar")
        }
}

interface State {
    fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding
}

class ActionBarStart(val title: Int, val hint: Int)
class ActionBarExt(
    private val frameLayout: FrameLayout,
    clazz: KClass<out Fragment>,
) {
    val search = MutableLiveData<IStringSearch>()
    private val routing = pairLookupOf<KClass<out Fragment>, ActionBarStart>(
        SachFragment::class to ActionBarStart(R.string.title_sach, R.string.hint_seach_sach),
        DocGiaFragment::class to ActionBarStart(R.string.title_docgia, R.string.hint_seach_docgia)
    )

    init {
        val mValue = routing.requireValueOf(clazz)
        val tieuDe = ActionBarTitleAndSearchButtonState(mValue)
        val timKiem = ActionBarInputSearchState(mValue)
        tieuDe.clickSearch = { setState(timKiem) }
        timKiem.exitSearch = { setState(tieuDe) }
        timKiem.onSearchListener = {
            search.postValue(
                getTypeSearch(it, clazz)
            )
        }
        setState(tieuDe)
    }

    private fun getTypeSearch(it: String, clazz: KClass<out Fragment>): IStringSearch? {
        if (clazz == SachFragment::class) return object : IsSachSearch {
            override val mValueSach: String = it
        }
        if (clazz == DocGiaFragment::class) return object : IsDocGiaSearch {
            override val mValueDocGia: String = it
        }
        return null
    }


    private fun setState(state: State) {
        if (state is AutoCloseable) {
            try {
                state.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        frameLayout.removeAllViews()
        state.onCreate(LayoutInflater.from(frameLayout.context), frameLayout)
            .apply { frameLayout.addView(this.root) }
    }
}

class ActionBarTitleAndSearchButtonState(actionBarStart: ActionBarStart) : State {
    private val title = actionBarStart.title
    lateinit var clickSearch: () -> Unit
    override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        TieuDeTimkiemBinding.inflate(inflater, parent, false).apply {
            this.tenFragment.setText(title)
            this.btnSearch.onClick { clickSearch() }
            return this
        }
    }
}

class ActionBarInputSearchState(actionBarStart: ActionBarStart) : State {
    private val hint = actionBarStart.hint
    lateinit var exitSearch: () -> Unit
    lateinit var onSearchListener: (String) -> Unit
    override fun onCreate(inflater: LayoutInflater, parent: ViewGroup): ViewBinding {
        TimkiemBinding.inflate(inflater, parent, false).apply {
            this.editseach.setHint(hint)
            this.editseach.addTextChangedListener {
                onSearchListener(it.toString())
                show(this.clearSearch, it?.length!! > 0)
            }
            this.offSearch.onClick {
                exitSearch()
            }
            this.clearSearch.onClick {
                this.editseach.setText("")
            }
            show(this.clearSearch, this.editseach.length() > 0)
            return this
        }
    }
}
