package com.dev.demoapp.dev.utils

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.dev.demoapp.R
import com.dev.demoapp.dev.common.AppResource
import com.dev.demoapp.dev.extensions.gone
import kotlinx.android.synthetic.main.layout_dialog_base.view.*
import kotlinx.android.synthetic.main.layout_dialog_base.view.buttonOk
import kotlinx.android.synthetic.main.layout_dialog_base.view.imageClose
import kotlinx.android.synthetic.main.layout_dialog_base.view.textTitle

object DialogUtil {

    var isCurrentLanguage = LanguageUtil.getCurrentLanguage()

    fun showDialog(
        context: Context?,
        title: String? = null,
        message: String? = null,
        cancelable: Boolean = true,
        okLabel: String? = null,
        okFunc: ((Unit) -> Unit)? = null,
        canFunc: ((Unit) -> Unit)? = null,
    ) {

        if (context == null) return

        val builder = AlertDialog.Builder(context, R.style.DialogAlert)
        val viewDialog = AppResource.getViewInflater(R.layout.layout_dialog_base, null)
        builder.setView(viewDialog)
        builder.setCancelable(cancelable)
        val alertDialog = builder.create()
        title?.let {
            viewDialog.textTitle.text = title
        }
        message?.let {
            viewDialog.textMessage.text = message
        }

        okLabel?.let {
            viewDialog.buttonOk.text = okLabel
        }

        viewDialog.buttonOk.setOnClickListener {
            okFunc?.invoke(Unit)
            alertDialog.dismiss()
        }

        viewDialog.imageClose.visibility = if (cancelable) View.VISIBLE else View.GONE
        viewDialog.imageClose.setOnClickListener {
            canFunc?.invoke(Unit)
            alertDialog.dismiss()
        }
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
    }

    fun showDialog(
        context: Context?,
        @StringRes titleRes: Int? = null,
        @StringRes messageRes: Int? = null,
        cancelable: Boolean = true,
        isShowCloseImage: Boolean = true,
        @StringRes okLabelRes: Int? = null,
        okFunc: ((Unit) -> Unit)? = null
    ) {

        if (context == null) return

        val builder = AlertDialog.Builder(context, R.style.DialogAlert)
        val viewDialog = AppResource.getViewInflater(R.layout.layout_dialog_base, null)
        builder.setView(viewDialog)
        builder.setCancelable(cancelable)
        val alertDialog = builder.create()

        if (!isShowCloseImage) {
            viewDialog.imageClose.gone()
        }

        titleRes?.let {
            viewDialog.textTitle.text = AppResource.getString(it)
        }
        messageRes?.let {
            viewDialog.textMessage.text = AppResource.getString(it)
        }

        okLabelRes?.let {
            viewDialog.buttonOk.text = AppResource.getString(it)
        }

        viewDialog.buttonOk.setOnClickListener {
            okFunc?.invoke(Unit)
            alertDialog.dismiss()
        }

        viewDialog.imageClose.visibility = if (cancelable) View.VISIBLE else View.GONE
        viewDialog.imageClose.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

}
