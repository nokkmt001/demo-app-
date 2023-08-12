package com.dev.demoapp.dev.xbase

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dev.demoapp.R
import com.dev.demoapp.dev.common.AppPermission
import com.dev.demoapp.dev.event.NetworkChangeEvent
import com.dev.demoapp.dev.extensions.*
import com.dev.demoapp.dev.extensions.lifecycle.ResultLifecycle
import com.dev.demoapp.dev.extensions.lifecycle.ResultRegistry
import com.dev.demoapp.dev.extensions.lifecycle.ViewLifecycleOwner

import com.dev.demoapp.dev.utils.ScreenUtil
import com.dev.demoapp.dev.utils.doCatch
import com.dev.demoapp.view.ui.main.MainActivity
import com.skydoves.bindables.BindingFragment
import kotlinx.coroutines.flow.StateFlow
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import timber.log.Timber
import java.lang.ref.SoftReference
import java.util.*

abstract class BaseMvvmFragment<T : ViewDataBinding>(@LayoutRes private val contentLayoutId: Int) :
    BindingFragment<T>(contentLayoutId), Dispatcher {
    private val TAG: String = this.javaClass.simpleName

    companion object {
        private const val STATE_INVISIBLE = 1
        private const val STATE_VISIBLE = 0
        private const val STATE_NONE = -1
    }

    var progressDialog: Dialog? = null


    private var progressBar = Int

    /**
     * use or not EventBus
     */
    open fun useEventBus(): Boolean = false

    /**
     * check animation
     */
    var isInLeft: Boolean = false
    var isOutLeft: Boolean = false
    var isCurrentScreen: Boolean = false //@FragmentHelper

    val viewLife = ViewLifecycleOwner()

    val resultLife: ResultLifecycle = ResultRegistry()

    private val mLifeRegistry get() = viewLife.lifecycle
    private var mVisibleState = STATE_NONE

    private var isConnected = true

    private var mExitTime: Long = 0

    private var fragmentView: SoftReference<View>? = null

    private var isLowMemory = false

//    abstract fun getVM(): Class<VM>

    fun <T : ViewModel> getViewModel(className: Class<T>): T? {
        return activity?.let { ViewModelProvider(it)[className] }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        doCatch({firstFlow()})

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val saveData = setSaveData()
        if (saveData != null) {
            outState.putAll(saveData)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if (savedInstanceState != null) onLoadSaveData(savedInstanceState)
            doCatch({startFlow()})
            doCatch({initData()})
            doCatch({setUpAction()})
            doCatch({ initVM()})
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    suspend inline fun <T> StateFlow<T>.observe(crossinline listener: (T) -> (Unit)) {
        collect { listener.invoke(it) }
    }

    open fun showNoData() {

    }

    open fun showLoading() {

    }

    open fun showProgressDialog(isShow: Boolean) {
        if (isShow) {
            progressDialog = Dialog(requireContext())
            progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            Objects.requireNonNull<Window>(progressDialog!!.window)
                .setBackgroundDrawableResource(R.color.colorTransparent)
            progressDialog!!.setCancelable(false)
            progressDialog?.setContentView(R.layout.dialog_progressbar_waiting)
            progressDialog!!.show()
        } else {
            if (progressDialog != null) {
                progressDialog!!.dismiss()
            }
        }
    }

    protected fun onLoadSaveData(savedInstanceState: Bundle) {}

    override fun onLowMemory() {
        super.onLowMemory()
        clearViews()
        onStart()
    }

    final override fun onStart() {
        super.onStart()
        if (isVisibleOnScreen()) {
            performStartFragment()
            mLifeRegistry.start()
            Timber.tag(TAG).i("Start")
        }
    }

    final override fun onResume() {
        super.onResume()
        if (isVisibleOnScreen()) {
            onFragmentResumed()
            mLifeRegistry.resume()
            Timber.tag(TAG).i("Resume")
        }
    }

    final override fun onPause() {
        super.onPause()
        if (isVisibleOnScreen()) {
            onFragmentPaused()
            mLifeRegistry.pause()
            Timber.tag(TAG).i("Pause")
        }
    }

    final override fun onStop() {
        super.onStop()
        if (isVisibleOnScreen()) {
            performStopFragment()
            mLifeRegistry.stop()
            Timber.tag(TAG).i("Stop")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        mLifeRegistry.destroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    open fun firstFlow() {}

    open fun startFlow() {}

    open fun initData() {}

    open fun setUpAction() {}

    open fun initVM() {}

    protected open fun onFragmentStarted() {}

    protected open fun onFragmentStopped() {}

    protected open fun onFragmentResumed() {}

    protected open fun onFragmentPaused() {}

    private fun performStopFragment() {
        onFragmentStopped()
        mVisibleState = STATE_INVISIBLE
    }

    private fun performStartFragment() {
        onFragmentStarted()
        mVisibleState = STATE_VISIBLE
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (mVisibleState == (if (hidden) STATE_INVISIBLE else STATE_VISIBLE)) return

        if (hidden) {
            onFragmentPaused()
            mLifeRegistry.pause()
            performStopFragment()
            mLifeRegistry.stop()
            Timber.tag(TAG).i("Hide")
        } else {
            performStartFragment()
            mLifeRegistry.start()
            onFragmentResumed()
            mLifeRegistry.resume()
            Timber.tag(TAG).i("Show")
        }
        dispatchHidden(hidden)
    }

    /**
     * goto @baseFragment
     */
    fun pushFragment(baseFragment: BaseMvvmFragment<*>) {
        (activity as BaseMvvmActivity<*>).pushFragment(baseFragment)
    }

    /**
     * remove fragment in back stack
     */
    fun popFragment() {
        (activity as BaseMvvmActivity<*>).popFragment()
    }

    /**
     * goto @baseFragment fragment in back stack (it will remove back fragment in back stack)
     * ex: backstack[0,1,2,3] -> popToFragment(1) --> backstack[0,1] (fragment 2,3 will be removed)
     */
    fun popToFragment(baseFragment: BaseMvvmFragment<*>) {
        (activity as BaseMvvmActivity<*>).popToFragment(baseFragment)
    }

    /**
     * remove all fragment in back stack (except main fragments)
     */
    fun popToRootFragment() {
        (activity as BaseMvvmActivity<*>).popToRootFragment()
    }

    /**
     * remove current fragment and goto @baseFragment
     */
    fun popAndPushNewFragment(baseFragment: BaseMvvmFragment<*>) {
        (activity as BaseMvvmActivity<*>).popAndPushNewFragment(baseFragment)
    }

    fun replaceAndAddNewFragment(baseFragment: BaseMvvmFragment<*>) {
        (activity as BaseMvvmActivity<*>).replaceAndAddNewFragment(baseFragment)
    }

    fun clearCurrentFragment(){
        (activity as BaseMvvmActivity<*>).clearCurrentFragment()
    }

    /**
     * No network status -> automatic reconnection operation with network status, subclass can override this method
     */
    open fun doReConnected() {

    }

    open fun doDisConnected() {

    }

    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        isConnected = event.isConnected
        if (event.isConnected) {
            doReConnected()
        } else {
            doDisConnected()
        }
    }

    fun setSaveData(): Bundle? {
        return null
    }

    private fun clearViews() {
        if (fragmentView != null) fragmentView!!.clear()
        fragmentView = null
        isLowMemory = true
    }

    protected open fun myActivity(): MainActivity? {
        try {
            return activity as MainActivity?
        } catch (ignored: java.lang.Exception) {
        }
        return null
    }

    fun lockScreen(isLock: Boolean) {
        ScreenUtil.lockScreen(activity, isLock)
    }

    fun onPermissions(vararg permissions: String, callback: (() -> Unit)) =
        AppPermission.request(activity, *permissions, callback = callback)

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        val animation: Animation?
        val isPushFragment = (activity as BaseMvvmActivity<*>).isPushFragment

        animation = if (isPushFragment) {
            if (isCurrentScreen) {
                AnimationUtils.loadAnimation(activity, R.anim.slide_in_right)
            } else {
                null
            }
        } else {
            if (isCurrentScreen) {
                null
            } else {
                AnimationUtils.loadAnimation(activity, R.anim.slide_out_right)
            }
        }
        return animation
    }

}