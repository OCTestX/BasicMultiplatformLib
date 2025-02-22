package io.github.kotlin.fibonacci.logger

import io.github.kotlin.fibonacci.appDirs
import io.github.kotlin.fibonacci.utils.asFilePath
import io.github.kotlin.fibonacci.utils.linkFile
import io.github.kotlin.fibonacci.utils.mustFile
import io.github.kotlin.fibonacci.utils.sink
import io.klogging.Level
import io.klogging.Level.DEBUG
import io.klogging.config.ANSI_CONSOLE
import io.klogging.config.SinkConfiguration
import io.klogging.config.loggingConfiguration
import io.klogging.noCoLogger
import io.klogging.rendering.RENDER_ANSI
import io.klogging.sending.SendString
import kotlinx.io.buffered
import kotlinx.io.writeString
import java.lang.System

object OLogger {
    private val ologger = noCoLogger<OLogger>()
    internal fun init() {
        loggingConfiguration {
            ANSI_CONSOLE()
            this.kloggingMinLogLevel(Level.DEBUG)
        }
        val logFile = appDirs.getUserLogDir().asFilePath().linkFile(System.nanoTime().toString()+".log").mustFile()
        ologger.info { "日志文件位置: $logFile" }
        ologger.info { "getSharedDir:"+appDirs.getSharedDir() }
        ologger.info { "getUserCacheDir:"+appDirs.getUserCacheDir() }
        ologger.info { "getUserDataDir:"+appDirs.getUserDataDir() }
        ologger.info { "getSiteConfigDir:"+appDirs.getSiteConfigDir() }
        ologger.info { "getSiteDataDir:"+appDirs.getSiteDataDir() }
        ologger.info { "getUserConfigDir:"+appDirs.getUserConfigDir() }
        loggingConfiguration(append = true) {
            val filePrinter = logFile.sink().buffered()
            val fileSendString = SendString {
                    eventString -> filePrinter.writeString(eventString)
                filePrinter.flush()
            }
            val fileSink = SinkConfiguration(RENDER_ANSI, fileSendString)
            sink("file", fileSink)
            logging { fromMinLevel(DEBUG) { toSink("file") } }
        }
        Thread.setDefaultUncaughtExceptionHandler(Inc.GlobalExceptionHandler(Thread.getDefaultUncaughtExceptionHandler()?:Inc.NOPHandle()))
        ologger.info("Initialized")
    }
    private object Inc {
        class NOPHandle() : Thread.UncaughtExceptionHandler {
            override fun uncaughtException(t: Thread?, e: Throwable?) {
            }
        }
        class GlobalExceptionHandler(val default: Thread.UncaughtExceptionHandler) : Thread.UncaughtExceptionHandler by default {
            private val ologger = noCoLogger<GlobalExceptionHandler>()
            override fun uncaughtException(thread: Thread, exception: Throwable) {
                ologger.error(exception) {
                    "The uncaught exception in thread ${thread.name}: ${exception.message}"
                }
                default.uncaughtException(thread, exception)
            }
        }
    }
}