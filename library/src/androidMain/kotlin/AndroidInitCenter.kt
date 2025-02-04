package io.github.kotlin.fibonacci

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