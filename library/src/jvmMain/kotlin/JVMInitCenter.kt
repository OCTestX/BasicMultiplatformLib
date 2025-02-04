package io.github.kotlin.fibonacci

object JVMInitCenter {
    private var initialized = false
    fun init() {
        CommonInitCenter.init {

        }
    }
}