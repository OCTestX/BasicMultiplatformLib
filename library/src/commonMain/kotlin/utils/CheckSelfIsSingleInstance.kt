package io.github.kotlin.fibonacci.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.ServerSocket

private var socketServer: ServerSocket? = null
suspend fun checkSelfIsSingleInstance(): Boolean = withContext(Dispatchers.IO) {
    if (socketServer != null) return@withContext true
    return@withContext try {
        socketServer = ServerSocket(19501)//TODO change port
        true
    } catch (e: Throwable) {
        false
    }
}