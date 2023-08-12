package com.dev.demoapp.dev.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun formatDateToString(date: Date?, formatOut: String?): String? {
        return try {
            val simpleDateFormat = SimpleDateFormat(formatOut)
            return simpleDateFormat.format(date)
        } catch (e: java.lang.Exception) {
            ""
        }
    }

    fun formatStringToDateRequestNew(string: String?, formatIn: String?): String? {
        val originalFormat: DateFormat = SimpleDateFormat(formatIn)
        val targetFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var date: Date? = null
        return try {
            date = originalFormat.parse(string)
            targetFormat.format(date)
        } catch (e: ParseException) {
            null
        }
    }

    fun formatStringToDateUtil(strDate: String?, formatIn: String?): Date? {
        return try {
            val sdf1 = SimpleDateFormat(formatIn)
            sdf1.parse(strDate)
        } catch (e: ParseException) {
            null
        }
    }

    fun getListVehicleName(): List<String> {
        val list = ArrayList<String>()
        list.add("Xe máy")
        list.add("Xe ba gác")
        list.add("Xe ô tô")
        list.add("Xe tải 1 tấn")
        list.add("Xe tải 2 tấn")
        list.add("Xe tải trên 3 tấn")
        list.add("Xe khác")
        return list
    }

    fun formatTextUnderline(
        view: TextView,
        text: String,
        label: String?,
        @ColorRes colorRes: Int
    ) {
        if (label.isNullOrEmpty()) {
            view.text = text
        } else {
            val spannable = SpannableString(text)
            val index = text.indexOf(label)
            val color = ContextCompat.getColor(view.context, colorRes)

            spannable.setSpan(
                UnderlineSpan(),
                index,
                index + label.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                ForegroundColorSpan(color),
                index,
                index + label.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                index,
                index + label.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            view.setText(spannable, TextView.BufferType.SPANNABLE)
        }

    }

}