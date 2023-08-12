package com.dev.demoapp.dev.common

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.core.content.ContextCompat

object AppResource {

    lateinit var application: Application
    lateinit var packageName: String
    var appName: String = ""

    fun init(application: Application) {
        AppResource.application = application
        packageName = application.packageName
    }

    fun getViewInflater(@LayoutRes layoutRes: Int, container: ViewGroup?): View =
        application.let {

            val inflater =
                application.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(layoutRes, container, false)

        }

    @JvmStatic
    fun getColor(@ColorRes colorRes: Int): Int =
        application.let { ContextCompat.getColor(it, colorRes) }

    @JvmStatic
    fun getDrawable(@DrawableRes drawableRes: Int): Drawable? =
        application.let { ContextCompat.getDrawable(it, drawableRes) }

    fun getString(@StringRes stringRes: Int): String = application.getString(stringRes)

    fun getStringFormat(@StringRes stringRes: Int, args: Any): String = String.format(application.getString(stringRes), args)

    @JvmStatic
    fun getDimensionOffset(@DimenRes dimenRes: Int): Int =
        application.resources.getDimensionPixelOffset(dimenRes)


    fun getBuildConfigValue(fieldName: String): Any? {
        try {
            val clazz = Class.forName("$packageName.BuildConfig")
            val field = clazz.getField(fieldName)
            return field.get(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}