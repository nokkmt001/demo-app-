package com.dev.demoapp.view.ui.login

import android.widget.CompoundButton
import com.dev.demoapp.R
import com.dev.demoapp.database.prefs.Preference
import com.dev.demoapp.databinding.FragmentLoginBinding
import com.dev.demoapp.dev.extensions.doAfterTextChanged
import com.dev.demoapp.dev.extensions.launchWhenCreated
import com.dev.demoapp.dev.extensions.showToastLong
import com.dev.demoapp.dev.utils.AESUtils
import com.dev.demoapp.dev.xbase.BaseMvvmFragment
import com.dev.demoapp.model.RepoState
import com.dev.demoapp.model.UserLoginPass
import com.dev.demoapp.view.ui.main.MainFragment
import com.dev.demoapp.view.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SignupFragment :
    BaseMvvmFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModel()

    private var userName = ""
    private var passWord = ""

    override fun startFlow() {
        super.startFlow()

        binding.editEmail.doAfterTextChanged {
            validateLogin()
        }

        binding.editPassword.doAfterTextChanged {
            validateLogin()
        }

        binding.btnLogin.setOnClickListener {
            viewModel.checkLogin(getParamLogin())
        }

        binding.checkbox.setOnCheckedChangeListener { p0, p1 -> validateLogin() }
        validateLogin()

    }

    private fun getParamLogin(): UserLoginPass {
        userName = binding.editEmail.text.toString().trim()
        passWord = binding.editPassword.text.toString().trim()
        return UserLoginPass(userName, passWord)

    }

    override fun initVM() {
        super.initVM()
        launchWhenCreated {
            viewModel.eventData.collect {
                if (it.state == RepoState.SUCCESS) {
                    Preference.saveUserName(AESUtils.encrypt(userName))
                    Preference.savePassWord(AESUtils.encrypt(passWord))
                    Preference.saveToken(it.result?.accessToken)
                    Preference.isLogin(true)
                    Preference.saveUser(it.result?.user)
                    replaceAndAddNewFragment(MainFragment())
                } else if (it.state == RepoState.FAIL) {
                    if (it.message!!.isEmpty()) return@collect
                    showToastLong(getString(R.string.error_login_fails))
                }
            }
        }

        launchWhenCreated {
            viewModel.eventLoading.observe {
                lockScreen(it)
                showProgressDialog(it)
            }
        }
    }

    private fun validateLogin() {
        binding.btnLogin.isEnabled =
            !(binding.editEmail.text.isNullOrEmpty() && binding.editPassword.text.isNullOrEmpty() && !binding.checkbox.isChecked)
    }

}
