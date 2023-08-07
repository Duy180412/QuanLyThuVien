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
    operator fun invoke(stringId: StringId, text: String): String {
        val resID = getResId(stringId)
        return resources.getString(resID, text.checkStringNull())
    }

    private fun getResId(stringId: StringId): Int {
        return when (stringId) {
            StringId.MaxInt -> R.string.hintfeilds_maxint
        }
    }
}