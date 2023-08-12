package com.dev.demoapp.dev.common

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dev.demoapp.R
import com.dev.demoapp.model.enum.RequestCode

object AppPermission {

    var callback: (() -> Unit)? = null

    fun request(
        activity: Activity?,
        vararg permissions: String,
        callback: (() -> Unit)
    ) {
        if (activity == null) return

        val requestList = mutableListOf<String>()
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED)
                requestList.add(it)
        }

        if (requestList.isEmpty()) {
            callback.invoke()
        } else {

            val requestRationaleList = mutableListOf<String>()
            requestList.forEach {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, it))
                    requestRationaleList.add(it)
            }

            if (requestRationaleList.isNotEmpty()) {
                showRequireMessage()
            }

            AppPermission.callback = callback
            ActivityCompat.requestPermissions(activity, requestList.toTypedArray(), RequestCode.PERMISSION.value)
        }
    }

    fun request(
        fragment: Fragment?,
        vararg permissions: String,
        callback: (() -> Unit)
    ) {
        if (fragment == null || fragment.context == null) return

        val requestList = mutableListOf<String>()
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(fragment.requireContext(), it) != PackageManager.PERMISSION_GRANTED)
                requestList.add(it)
        }

        if (requestList.isEmpty()) {
            callback.invoke()
        } else {

            val requestRationaleList = mutableListOf<String>()
            requestList.forEach {
                if (ActivityCompat.shouldShowRequestPermissionRationale(fragment.requireActivity(), it))
                    requestRationaleList.add(it)
            }

            if (requestRationaleList.isNotEmpty()) {
                showRequireMessage()
            }

            AppPermission.callback = callback
            fragment.requestPermissions(requestList.toTypedArray(), RequestCode.PERMISSION.value)
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == RequestCode.PERMISSION.value) {
            if (grantResults.isNotEmpty()) {
                var isSuccess = true
                grantResults.forEach {
                    isSuccess = isSuccess && (it == PackageManager.PERMISSION_GRANTED)
                }
                if (isSuccess) {
                    callback?.invoke()
                    callback = null
                } else {
                    showRequireMessage()
                }
            }
        }
    }

    private fun showRequireMessage() {
        Toast.makeText(AppResource.application, R.string.permission_required, Toast.LENGTH_SHORT).show()
    }

    fun clear() {
        callback = null
    }
}