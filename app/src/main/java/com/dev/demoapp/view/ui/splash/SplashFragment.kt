package com.dev.demoapp.view.ui.splash

import android.os.Looper
import com.dev.demoapp.R
import com.dev.demoapp.database.prefs.Preference
import com.dev.demoapp.databinding.FragmentSplashBinding
import com.dev.demoapp.dev.extensions.gone
import com.dev.demoapp.dev.extensions.launchWhenCreated
import com.dev.demoapp.dev.utils.AESUtils
import com.dev.demoapp.dev.xbase.BaseMvvmFragment
import com.dev.demoapp.model.RepoState
import com.dev.demoapp.model.UserLoginPass
import com.dev.demoapp.view.ui.home.HomeFragment
import com.dev.demoapp.view.ui.login.LoginFragment
import com.dev.demoapp.view.ui.main.MainFragment
import com.dev.demoapp.view.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment :
    BaseMvvmFragment<FragmentSplashBinding>(R.layout.fragment_splash) {
    private val viewModel: LoginViewModel by viewModel()

    override fun startFlow() {
        super.startFlow()
        checkLogin()
    }

    private fun getParamLogin(): UserLoginPass {
        val userName = AESUtils.decrypt(Preference.getUserName())
        val passWord = AESUtils.decrypt(Preference.getPassword())
        return UserLoginPass(
            userName,
            passWord
        )
    }


    override fun initVM() {
        super.initVM()
        launchWhenCreated {
            viewModel.eventData.collect {
                if (it.state == RepoState.SUCCESS) {
                    Preference.saveToken(it.result?.accessToken)
                    Preference.saveUser(it.result?.user)
                    replaceAndAddNewFragment(HomeFragment())
                } else if (it.state == RepoState.FAIL) {
                    replaceAndAddNewFragment(LoginFragment())
                }
            }
        }
    }

    private fun checkLogin() {
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            binding.progressLoading.gone()
            if (Preference.getLogin()) {
                viewModel.checkLogin(getParamLogin())
            } else {
                replaceAndAddNewFragment(LoginFragment())
            }
        }, 1500)
    }
}
