package com.dev.demoapp.view.ui.home

import androidx.recyclerview.widget.GridLayoutManager
import com.dev.demoapp.R
import com.dev.demoapp.database.prefs.Preference
import com.dev.demoapp.databinding.FragmentHomeBinding
import com.dev.demoapp.dev.extensions.launchWhenCreated
import com.dev.demoapp.dev.utils.ContainsUtils
import com.dev.demoapp.dev.xbase.BaseMvvmFragment
import com.dev.demoapp.model.Categories
import com.dev.demoapp.model.RepoState
import com.dev.demoapp.view.ui.adapter.CategoriesAdapter
import com.dev.demoapp.view.ui.login.LoginFragment
import com.dev.demoapp.view.ui.main.MainFragment
import com.dev.demoapp.view.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseMvvmFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val profile = Preference.getUser()

    private var adapter = CategoriesAdapter()

    private val viewModel: MainViewModel by viewModel()

    override fun startFlow() {
        super.startFlow()
        binding.rclMain.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }

        binding.layoutHeader.textChoose.setOnClickListener {
            pushFragment(MainFragment())
        }

        binding.layoutHeader.imageBack.setOnClickListener {
            popAndPushNewFragment(LoginFragment())
        }

        adapter.clear()
        viewModel.getCategories(getHeaders())
    }

    override fun initVM() {
        super.initVM()
        launchWhenCreated {
            viewModel.eventData.observe {
                if (it.state == RepoState.SUCCESS) {
                    it.result?.let { it1 -> handleData(data = it1) }
                } else {

                }
            }
        }
    }


    private fun handleData(data: List<Categories>) {
        adapter.clear()
        adapter.addAll(data)
    }

    override fun setUpAction() {
        super.setUpAction()
    }

    private fun getHeaders(): HashMap<String, Any> {
        val headers = HashMap<String, Any>()
        headers[ContainsUtils.HEADER_AUTHORIZATION] = "Bearer " + Preference.getToken()
        return headers
    }

}
