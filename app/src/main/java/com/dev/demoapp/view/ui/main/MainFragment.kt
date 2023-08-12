package com.dev.demoapp.view.ui.main

import com.dev.demoapp.R
import com.dev.demoapp.databinding.FragmentMainBinding
import com.dev.demoapp.dev.xbase.BaseMvvmFragment
import com.dev.demoapp.dev.xbase.EmptyViewModel
import com.dev.demoapp.view.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment :
    BaseMvvmFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private var viewModelM: EmptyViewModel? = null

    private val fragmentHome = HomeFragment()

//    private val fragmentSetting = SettingFragment()

    override fun startFlow() {
        super.startFlow()
        viewModelM = getViewModel(EmptyViewModel::class.java)
        loadFragment(fragmentHome)
        main_bottom_navigation.itemIconTintList = null
        main_bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    loadFragment(fragmentHome)
                }

                R.id.action_setting -> {
//                    loadFragment(fragmentSetting)
                }

            }
            true
        }

    }

    private fun loadFragment(fragment: BaseMvvmFragment<*>) {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.mainPager, fragment)
            commit()
        }

    }

}
