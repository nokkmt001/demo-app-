package com.dev.demoapp.dev.xbase

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.dev.demoapp.R
import com.dev.demoapp.dev.extensions.hideKeyboard
import com.dev.demoapp.dev.utils.DialogUtil


abstract class BaseMainActivity<T : ViewDataBinding>(@LayoutRes private val contentLayoutId: Int)  : BaseMvvmActivity<T>(contentLayoutId),
    FragmentHelper.FragmentAction {

    private var mFragmentHelper: FragmentHelper? = null

    private var mExitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentHelper = FragmentHelper(this)
    }

    override fun onBackPressed() {
        hideKeyboard()
        if (mFragmentHelper!!.currentPageSize > 1) {
            popFragment()
            return
        }

//        if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
            DialogUtil.showDialog(this, getString(R.string.notification), getString(R.string.tittle_out_of_application), true, null, okFunc = {
                mFragmentHelper!!.remove()
                finishAffinity()
            })
//        } else {
//            mExitTime = System.currentTimeMillis()
//        }
    }

    override fun pushFragment(baseFragment: BaseMvvmFragment<*>) {
        super.pushFragment(baseFragment)
        mFragmentHelper!!.pushFragment(baseFragment)
    }

    override fun popFragment() {
        super.popFragment()
        mFragmentHelper!!.popFragment()
    }

    override fun popToFragment(baseFragment: BaseMvvmFragment<*>) {
        super.popToFragment(baseFragment)
        mFragmentHelper!!.popToFragment(baseFragment::class.java)
    }

    override fun popToRootFragment() {
        super.popToRootFragment()
        mFragmentHelper!!.popFragmentToRoot()
    }

    override fun popAndPushNewFragment(baseFragment: BaseMvvmFragment<*>) {
        super.popAndPushNewFragment(baseFragment)
        mFragmentHelper!!.popAndPushNewFragment(baseFragment)
    }

    override fun replaceAndAddNewFragment(baseFragment: BaseMvvmFragment<*>) {
        super.replaceAndAddNewFragment(baseFragment)
        mFragmentHelper!!.replaceFragment(baseFragment)
    }

    override fun clearCurrentFragment() {
        super.clearCurrentFragment()
        mFragmentHelper!!.clearCurrentFragment()
    }

    fun getFragmentHelper() = mFragmentHelper

}
