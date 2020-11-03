package com.codExalters.androidutilities

import android.content.Context
import android.content.res.Configuration
import java.util.*

/**
 * Created by Ashish on 19/2/20.
 */
object LocaleManager {
    private fun updateResources(
        context: Context,
        language: String
    ): Context {

        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    fun setLocale(c: Context): Context {
        return setNewLocale(c, getLanguage(c))
    }

    fun setNewLocale(c: Context, language: String): Context {
        persistLanguage(c, language)
        return updateResources(c, language)
    }

    private fun getLanguage(c: Context): String {
        return MyAppPreferenceUtils.getAppLanguage(c)
    }

    private fun persistLanguage(c: Context, language: String) {
        MyAppPreferenceUtils.setAppLanguage(c, language)

    }
}