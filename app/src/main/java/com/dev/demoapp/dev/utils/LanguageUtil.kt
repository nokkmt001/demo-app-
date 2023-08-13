package com.dev.demoapp.dev.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import com.dev.demoapp.R
import com.dev.demoapp.database.prefs.Preference
import com.dev.demoapp.dev.common.AppResource
import com.dev.demoapp.model.Language
import com.dev.demoapp.model.LanguageCode
import java.util.*

class LanguageUtil(base: Context) : ContextWrapper(base) {

    companion object {

        private val listLanguage = mutableListOf(
            Language(1, R.string.language_english, LanguageCode.ENGLISH),
            Language(2, R.string.language_vietnamese, LanguageCode.VIETNAMESE),
        )

        fun getLanguageList(): List<Language> = listLanguage

        fun getCurrentLanguage(): Language = Preference.getLanguage()

        fun getLanguageDefault(): Language = listLanguage[0]

        fun loadLanguage(resources: Resources) {
            val locale = Locale(Preference.getLanguage().code)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            AppResource.application.resources.updateConfiguration(config, resources.displayMetrics)
        }

    }

}
