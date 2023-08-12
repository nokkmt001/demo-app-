package com.dev.demoapp.dev.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/** makes visible a view. */
fun View.visible() {
  visibility = View.VISIBLE
}

/** makes gone a view. */
fun View.gone(shouldBeGone: Boolean) {
  if (shouldBeGone) visibility = View.GONE
  else visible()
}

fun View.isVisible(boolean: Boolean) {
  visibility = if (boolean) View.VISIBLE else View.GONE
}

fun View.invisible() {
  visibility = View.INVISIBLE
}

fun View.visibleView() {
  visibility = View.INVISIBLE
}

fun View.gone() {
  visibility = View.GONE
}

fun ViewGroup.setContentView(@LayoutRes id: Int) {
  LayoutInflater.from(context).inflate(id, this, true)
}
