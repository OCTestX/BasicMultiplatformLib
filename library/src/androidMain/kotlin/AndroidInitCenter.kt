package io.github.octestx.basic.multiplatform.common

import android.app.Application

lateinit var applicationContext: Application
object AndroidInitCenter {
    private var initialized = false
    fun init(application: Application) {
        CommonInitCenter.init {
            applicationContext = application
        }
    }
}