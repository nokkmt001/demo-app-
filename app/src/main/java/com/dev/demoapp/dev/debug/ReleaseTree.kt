package com.dev.demoapp.dev.debug

import android.util.Log
import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN)
            Timber.log(priority, tag, message);
    }
}

class DebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format("C:%s:%s",
            super.createStackElementTag(element),
            element.lineNumber
        )
    }
}