package com.dev.demoapp.view.ui.login

import com.dev.demoapp.R
import com.dev.demoapp.database.prefs.Preference
import com.dev.demoapp.databinding.FragmentSignupBinding
import com.dev.demoapp.dev.extensions.doAfterTextChanged
import com.dev.demoapp.dev.extensions.launchWhenCreated
import com.dev.demoapp.dev.extensions.showToastLong
import com.dev.demoapp.dev.utils.AESUtils
import com.dev.demoapp.dev.xbase.BaseMvvmFragment
import com.dev.demoapp.model.RepoState
import com.dev.demoapp.model.UserLoginPass
import com.dev.demoapp.model.UserSignupRequest
import com.dev.demoapp.view.ui.main.MainFragment
import com.dev.demoapp.view.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SignupFragment :
    BaseMvvmFragment<FragmentSignupBinding>(R.layout.fragment_signup) {

    private val viewModel: LoginViewModel by viewModel()

    private var email = ""
    private var passWord = ""
    private var firstName = ""
    private var lastName = ""
    override fun startFlow() {
        super.startFlow()

        binding.editEmail.doAfterTextChanged {
            validateSignup()
        }

        binding.editPassword.doAfterTextChanged {
            validateSignup()
        }

        binding.editFirstName.doAfterTextChanged {
            validateSignup()
        }

        binding.editLastName.doAfterTextChanged {
            validateSignup()
        }

        binding.btnLogin.setOnClickListener {
            viewModel.signUp(getParamSignup())
        }

        binding.textLogin.setOnClickListener {
            popAndPushNewFragment(LoginFragment())
        }

        binding.checkbox.setOnCheckedChangeListener { p0, p1 -> validateSignup() }
        validateSignup()

    }

    private fun getParamSignup(): UserSignupRequest {
        email = binding.editEmail.text.toString().trim()
        passWord = binding.editPassword.text.toString().trim()
        firstName = binding.editFirstName.text.toString()
        lastName = binding.editLastName.text.toString()
        return UserSignupRequest(email, passWord, firstName, lastName)

    }

    private fun getParamLogin(): UserLoginPass {
        email = binding.editEmail.text.toString().trim()
        passWord = binding.editPassword.text.toString().trim()
        return UserLoginPass(email, passWord)
    }

    override fun initVM() {
        super.initVM()
        launchWhenCreated {
            viewModel.eventData.collect {
                if (it.state == RepoState.SUCCESS) {
                    Preference.saveUserName(AESUtils.encrypt(email))
                    Preference.savePassWord(AESUtils.encrypt(passWord))
                    Preference.saveToken(it.result?.accessToken)
                    Preference.isLogin(true)
                    Preference.saveUser(it.result?.user)
                    replaceAndAddNewFragment(MainFragment())
                } else if (it.state == RepoState.FAIL) {
                    if (it.message!!.isEmpty()) return@collect
                    showToastLong(getString(R.string.error_signup_success_login_fails))
                    popAndPushNewFragment(LoginFragment())
                }
            }
        }

        launchWhenCreated {
            viewModel.eventDataSignup.collect {
                if (it.state == RepoState.SUCCESS) {
                    viewModel.checkLogin(getParamLogin())
                } else if (it.state == RepoState.FAIL) {
                    if (it.message!!.isEmpty()) return@collect
                    showToastLong(getString(R.string.error_signup_fails))
                }
            }
        }

        launchWhenCreated {
            viewModel.eventLoading.observe {
//                lockScreen(it)
//                showProgressDialog(it)
            }
        }
    }

    private fun validateSignup() {
        binding.btnLogin.isEnabled =
            !(binding.editEmail.text.isNullOrEmpty() && binding.editPassword.text.isNullOrEmpty() &&
                    binding.editFirstName.text.isNullOrEmpty() && binding.editLastName.text.isNullOrEmpty() && !binding.checkbox.isChecked)
    }

}
