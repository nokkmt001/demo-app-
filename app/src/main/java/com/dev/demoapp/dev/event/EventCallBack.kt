package com.dev.demoapp.dev.event

interface EventCallBack<T> {
    fun onCall(result: T)
}

interface EventScan<T> {
    fun onScan(result: T)
}