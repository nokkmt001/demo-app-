package com.dev.demoapp.dev.xbase

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.dev.demoapp.dev.common.AppPermission
import com.dev.demoapp.dev.extensions.initLaunch
import com.dev.demoapp.dev.extensions.showToastLong
import com.dev.demoapp.dev.utils.DelayUtil
import com.dev.demoapp.dev.utils.ScreenUtil
import com.skydoves.bindables.BindingDialogFragment
import kotlinx.coroutines.flow.StateFlow

open class BaseDialogFragment<T : ViewDataBinding>(@LayoutRes private val layoutId: Int, private val isDialogCancelable: Boolean = false) :
    BindingDialogFragment<T>(layoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCancelable(isDialogCancelable)
            setCanceledOnTouchOutside(isDialogCancelable)
            window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            startFlow()
            initData()
            initVM()
        } catch (e: Exception) {
            toast(e.message.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun <T : BaseMvvmViewModel> getSelfViewModel(
        className: Class<T>,
        lockScreenWhenLoading: Boolean? = null,
        observeEvents: Boolean = true
    ): T {
        return ViewModelProvider(this)[className].apply {
            if (observeEvents) {
                initLaunch(
                    {
                        eventLoading.collect {
                            lockScreenWhenLoading?.let {
                                lockScreen(it)
                            }
                        }
                    },
                    {
                        eventErrorMessage.collect {
                            if (it.isNotEmpty())
                                showToastLong(it)
                        }
                    }

                )

            }
        }
    }

    suspend inline fun <T> StateFlow<T>.observe(crossinline listener: (T) -> (Unit)) {
        collect { listener.invoke(it) }
    }

    private fun lockScreen(isLock: Boolean) {
        this.activity?.let {
            ScreenUtil.lockScreen(this.activity, isLock)
        }
    }

    fun onPermissions(vararg permissions: String, callback: (() -> Unit)) =
        AppPermission.request(this, *permissions, callback = callback)

    fun toast(@androidx.annotation.StringRes res: Int?) {
        res ?: return
        Toast.makeText(this.context, res, Toast.LENGTH_SHORT).show()
    }

    fun toast(text: String?) {
        text ?: return
        Toast.makeText(this.context, text, Toast.LENGTH_SHORT).show()
    }

    fun delay(millis: Long = DelayUtil.DEFAULT, func: () -> Unit) {
        Handler().postDelayed({ func.invoke() }, millis)
    }

    open fun startFlow() {}

    open fun initData() {}

    open fun initVM() {}
}