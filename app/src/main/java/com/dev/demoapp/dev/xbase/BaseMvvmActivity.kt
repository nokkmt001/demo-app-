package com.dev.demoapp.dev.xbase

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.PixelFormat
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.dev.demoapp.R
import com.dev.demoapp.database.prefs.Preference
import com.dev.demoapp.dev.common.AppPermission
import com.dev.demoapp.dev.event.NetworkChangeEvent
import com.dev.demoapp.dev.extensions.lifecycle.LifeRegister
import com.dev.demoapp.dev.extensions.lifecycle.ResultLifecycle
import com.dev.demoapp.dev.extensions.lifecycle.ResultRegistry
import com.dev.demoapp.dev.extensions.toastLong
import com.dev.demoapp.dev.utils.LanguageUtil

import com.dev.demoapp.model.Language
import com.skydoves.bindables.BindingActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseMvvmActivity<T : ViewDataBinding>(@LayoutRes private val contentLayoutId: Int) :
    BindingActivity<T>(contentLayoutId) {

    val resultLife: ResultLifecycle = ResultRegistry()

    val lifeRegister by lazy { LifeRegister.of(this) }

    var hasNetwork: Boolean = true

    private var isLowMemory = false

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        try {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED)
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            }

            LanguageUtil.loadLanguage(resources)
            initTipView()
            startFlow()
            eventLoading()
            initView()
            initData()
            initVM()
        } catch (e: Exception) {
            toastLong(e.message.toString())
        }
    }


    override fun finish() {
        super.finish()
        if (mTipView.parent != null) {
            mWindowManager.removeView(mTipView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

    open fun startFlow() {}

    open fun initView() {}

    open fun initData() {}

    open fun initVM() {}

    open fun useEventBus(): Boolean = false

    /**
     * goto @baseFragment
     */
    var isPushFragment = false
    open fun pushFragment(baseFragment: BaseMvvmFragment<*>) {
        isPushFragment = true
    }

    /**
     * remove fragment in back stack
     */
    open fun popFragment() {
        isPushFragment = false
    }

    /**
     * goto @baseFragment fragment in back stack (it will remove back fragment in back stack)
     * ex: backstack[0,1,2,3] -> popToFragment(1) --> backstack[0,1] (fragment 2,3 will be removed)
     */
    open fun popToFragment(baseFragment: BaseMvvmFragment<*>) {
        isPushFragment = false
    }

    /**
     * remove all fragment in back stack (except main fragments)
     */
    open fun popToRootFragment() {
        isPushFragment = false
    }

    open fun popAndPushNewFragment(baseFragment: BaseMvvmFragment<*>){
        isPushFragment = true
    }

    open fun replaceAndAddNewFragment(baseFragment: BaseMvvmFragment<*>){
        isPushFragment = true
    }

    open fun clearCurrentFragment() {
        isPushFragment = false
    }

    /**
     * Clear focus on touch outside for all EditText inputs.
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val key = currentFocus
            if (key is EditText) {
                val outRect = Rect()
                key.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    key.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(key.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun eventLoading() {

    }

    protected open fun onActivityBackPressed() {
        super.onBackPressed()
    }

    fun showLoading(show: Boolean) {

    }

    fun toast(@androidx.annotation.StringRes res: Int, long: Int) {
        Toast.makeText(this, res, long).show()
    }

    fun toast(text: String, long: Int) {
        Toast.makeText(this, text, long).show()
    }

    /**
     * Prompt View
     */
    protected lateinit var mTipView: View
    protected lateinit var mWindowManager: WindowManager
    protected lateinit var mLayoutParams: WindowManager.LayoutParams

    /**
     * Do you need to display TipView
     */
    open fun enableNetworkTip(): Boolean = true

    /**
     * No network status -> automatic reconnection operation with network status, subclass can override this method
     */
    open fun doReConnected() {

    }

    open fun doDisConnected() {

    }

    /**
     * initialization TipView
     */
    private fun initTipView() {
        mTipView = layoutInflater.inflate(R.layout.network_tip, null)
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT
        )
        mLayoutParams.gravity = Gravity.TOP
        mLayoutParams.x = 0
        mLayoutParams.y = 0
        mLayoutParams.windowAnimations = R.style.anim_float_view // add animations
    }

    private fun checkNetwork(isConnected: Boolean) {
        if (isConnected) {
            doReConnected()
            if (mTipView.parent != null) {
                mWindowManager.removeView(mTipView)
            }
        } else {
            if (mTipView.parent == null) {
                mWindowManager.addView(mTipView, mLayoutParams)
            }
        }
    }

    /**
     * Network Change From EventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        hasNetwork = event.isConnected
        checkNetwork(event.isConnected)
    }

    protected open fun setSaveData(): Bundle? {
        return null
    }

    protected open fun onLoadSaveData(data: Bundle?) {}

    fun onPermissions(vararg permissions: String, callback: (() -> Unit)) =
        AppPermission.request(this, *permissions, callback = callback)

    private fun changeLanguage(language: Language) {
        Preference.saveLanguage(language)
        LanguageUtil.loadLanguage(resources)
        /**
         * reload
         */
    }
}

