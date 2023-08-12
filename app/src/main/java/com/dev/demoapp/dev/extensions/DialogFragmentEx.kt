package com.dev.demoapp.dev.extensions

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope

fun DialogFragment.initLaunch(vararg listBlock: suspend CoroutineScope.() -> Unit) {
    listBlock.forEach {
        launchWhenCreated { it.invoke(this) }
    }
}

fun DialogFragment.launchWhenCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launchWhenCreated { block.invoke(this) }
}
