package io.github.octestx.basic.multiplatform.common.logger

import io.github.octestx.basic.multiplatform.common.appDirs
import io.github.octestx.basic.multiplatform.common.utils.*
import io.klogging.Level
import io.klogging.config.ANSI_CONSOLE
import io.klogging.config.SinkConfiguration
import io.klogging.config.loggingConfiguration
import io.klogging.events.LogEvent
import io.klogging.noCoLogger
import io.klogging.rendering.RENDER_ANSI
import io.klogging.sending.EventSender
import io.klogging.sending.SendElk
import io.klogging.sending.SendString
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.io.buffered
import kotlinx.io.writeString
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.sin

object OLogger {
    private val ologger = noCoLogger<OLogger>()

    internal fun init() {
        loggingConfiguration {
            ANSI_CONSOLE()
            this.kloggingMinLogLevel(Level.DEBUG)
        }

        // 确保日志目录存在
        val logDir = appDirs.getUserLogDir().asKFilePath().mustDir()

        val timestamp = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .let {
                "${it.year}-${it.monthNumber.toString().padStart(2, '0')}-${it.dayOfMonth.toString().padStart(2, '0')}_${it.hour.toString().padStart(2, '0')}-${it.minute.toString().padStart(2, '0')}-${it.second.toString().padStart(2, '0')}-${it.nanosecond.toString().padStart(3, '0').take(3)}"
            }



        val logFile = logDir.linkFile("$timestamp.log").mustFile()

        ologger.info { "日志文件位置: $logFile" }
        logAppDirectories()

        loggingConfiguration(append = true) {
            val filePrinter = logFile.sink().buffered()
            val fileSendString = SendString { eventString ->
                filePrinter.writeString(eventString)
                filePrinter.writeString("\n")
                filePrinter.flush()
            }
            val fileSink = SinkConfiguration(RENDER_ANSI, fileSendString)
            sink("file", fileSink)
            sink("Af", object :EventSender {
                override fun invoke(batch: List<LogEvent>) {
                    for (item in batch) {
                        platformPrintLog(item)
                    }
                }

            })
            logging { fromMinLevel(Level.DEBUG) { toSink("file") } }
        }

        Thread.setDefaultUncaughtExceptionHandler(
            Inc.GlobalExceptionHandler(Thread.getDefaultUncaughtExceptionHandler() ?: Inc.NOPHandler)
        )

        ologger.info("Initialized")
    }

    private fun logAppDirectories() {
        ologger.info { "getSharedDir:" + appDirs.getSharedDir() }
        ologger.info { "getUserCacheDir:" + appDirs.getUserCacheDir() }
        ologger.info { "getUserDataDir:" + appDirs.getUserDataDir() }
        ologger.info { "getSiteConfigDir:" + appDirs.getSiteConfigDir() }
        ologger.info { "getSiteDataDir:" + appDirs.getSiteDataDir() }
        ologger.info { "getUserConfigDir:" + appDirs.getUserConfigDir() }
    }

    private object Inc {
        object NOPHandler : Thread.UncaughtExceptionHandler {
            override fun uncaughtException(t: Thread, e: Throwable) = Unit
        }

        class GlobalExceptionHandler(private val default: Thread.UncaughtExceptionHandler) :
            Thread.UncaughtExceptionHandler {
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

expect fun platformPrintLog(log: LogEvent)
