package io.github.octestx.basic.multiplatform.common

object JVMInitCenter {
    private var initialized = false
    fun init() {
        CommonInitCenter.init {

        }
    }
}