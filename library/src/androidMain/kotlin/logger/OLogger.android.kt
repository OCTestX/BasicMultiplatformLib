package io.github.octestx.basic.multiplatform.common.logger

import android.util.Log
import io.klogging.Level.*
import io.klogging.events.LogEvent

actual fun platformPrintLog(log: LogEvent) {
    val tag = log.host + log.context
    val message = log.message
    when(log.level) {
        TRACE -> Log.v(tag, message)
        DEBUG -> Log.d(tag, message)
        INFO -> Log.i(tag, message)
        WARN -> Log.w(tag, message)
        ERROR -> Log.e(tag, message)
        FATAL -> Log.wtf(tag, message)
        NONE -> {}
    }
}