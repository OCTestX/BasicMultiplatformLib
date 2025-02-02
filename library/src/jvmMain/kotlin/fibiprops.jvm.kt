package io.github.kotlin.fibonacci

import io.klogging.noCoLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

actual val firstElement: Int = 2
actual val secondElement: Int = 3

// 使用者的自定义配置类
class CustomConfig : CommonInitCenter.DefaultLibConfiguration() {
    override val appName: String = "VKD"
}

fun main() {
    startKoin {
        modules(
            module {
                single<CommonInitCenter.LibConfiguration> { CustomConfig() }
            }
        )
    }
    CommonInitCenter.init()
    val ologger = noCoLogger("JVM-MAIN")
    ologger.info { appDirs.getUserLogDir() }
    Thread.sleep(1200)
}