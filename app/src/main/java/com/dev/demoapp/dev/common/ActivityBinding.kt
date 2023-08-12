package com.dev.demoapp.dev.common

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.dev.demoapp.R

@BindingAdapter("simpleToolbarWithHome", "simpleToolbarTitle", requireAll = true)
fun bindToolbarWithTitle(toolbar: MaterialToolbar, activity: AppCompatActivity, title: String?) {
  activity.simpleToolbarWithHome(toolbar, title)
}

private fun AppCompatActivity.simpleToolbarWithHome(
  toolbar: MaterialToolbar,
  title_: String? = ""
) {
  setSupportActionBar(toolbar)
  supportActionBar?.run {
    setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    setDisplayHomeAsUpEnabled(true)
    title = title_
  }
}
