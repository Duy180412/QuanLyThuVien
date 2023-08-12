package com.example.qltvkotlin.presentation.helper

import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.enumeration.StringId
import com.example.qltvkotlin.domain.helper.ActivityRetriever
import com.example.qltvkotlin.presentation.extension.checkStringNull

class AppStringResources {
    private val activityRetriever: ActivityRetriever = ActivityRetriever.shared
    private val resources = activityRetriever().resources

    companion object {
        val shared = AppStringResources()
    }

    operator fun invoke(stringId: StringId, text: String = ""): String {
        val resID = getResId(stringId)
        return resources.getString(resID, text.checkStringNull())
    }

    private fun getResId(stringId: StringId): Int {
        return when (stringId) {
            StringId.MaxInt -> R.string.hintfeilds_maxint
            StringId.TenSach -> R.string.view_ten_sach
            StringId.TenTacGia -> R.string.view_ten_tacgia
            StringId.TongSach -> R.string.view_tong
            StringId.ConLai -> R.string.view_conlai
            StringId.TenDocGia -> R.string.view_ten_docgia
            StringId.Sdt -> R.string.view_sdt
            StringId.HanDangKi -> R.string.view_han_dangki
            StringId.ChoThue -> R.string.view_dangthue
            StringId.HetHan -> R.string.status_hethan
            StringId.ConHan -> R.string.status_hethan
            StringId.Loai -> R.string.view_tong_loai
            StringId.TongMuon -> R.string.view_tong_muon
        }
    }
}