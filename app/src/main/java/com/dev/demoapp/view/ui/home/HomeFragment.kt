package com.dev.demoapp.view.ui.home

import com.dev.demoapp.R
import com.dev.demoapp.database.prefs.Preference
import com.dev.demoapp.databinding.FragmentHomeBinding
import com.dev.demoapp.dev.xbase.BaseMvvmFragment

class HomeFragment : BaseMvvmFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val profile = Preference.getUser()

    override fun initData() {
        super.initData()

    }

    override fun setUpAction() {
        super.setUpAction()
    }

}
