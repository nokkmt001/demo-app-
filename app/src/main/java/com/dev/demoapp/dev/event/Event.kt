package com.dev.demoapp.dev.event


class NetworkChangeEvent(var isConnected: Boolean)
class StartMainEvent(var isStart: Boolean = false)
class DownloadEvent(var downloadID: Long = 0L)
