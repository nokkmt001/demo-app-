package com.dev.demoapp.model

import androidx.annotation.StringRes

data class Language(
        val id: Int,
        @StringRes val name: Int,
        val code: String,
        var isChecked: Boolean = false
)

object LanguageCode {
        const val VIETNAMESE = "vi"
        const val ENGLISH = "en"
}
