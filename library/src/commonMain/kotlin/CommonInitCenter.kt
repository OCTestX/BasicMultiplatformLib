package io.github.kotlin.fibonacci

import ca.gosyer.appdirs.AppDirs
import io.github.kotlin.fibonacci.logger.OLogger
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

lateinit var appDirs: AppDirs private set
internal object CommonInitCenter {
    private var initialized = false
    // 延迟加载配置，优先使用注入的实例
    inline fun init(platformInit: () -> Unit) {
        if (initialized) return
        //TODO INIT
        val configuration = getKoinInjectedConfiguration()
        appDirs = AppDirs(configuration.appName, configuration.appAuthor, *configuration.extras)
        OLogger.init()
        platformInit()
        initialized = true
    }
    private fun getKoinInjectedConfiguration(): LibConfiguration {
        val current: LibConfiguration = try {
            // 尝试获取 Koin 实例
            val koin = GlobalContext.get()
            // 从 Koin 获取配置，若不存在则返回默认
            koin.getOrNull<LibConfiguration>() ?: DefaultLibConfiguration()
        } catch (e: Exception) {
            // Koin 未启动或其他异常时返回默认
            DefaultLibConfiguration()
        }
        return current
    }
}
// 库的 Koin 模块，声明默认配置
val libKoinModule = module {
    // 使用单例，允许使用者覆盖
    single<LibConfiguration> { DefaultLibConfiguration() }
}
// 定义配置接口
interface LibConfiguration {
    //ConfigAppDir
    val appName: String
    val appAuthor: String
    val extras: Array<String>
}
// 默认配置实现
open class DefaultLibConfiguration : LibConfiguration {
    override val appName: String = (this::class.java).packageName
    override val appAuthor: String = "OCTest"
    override val extras: Array<String> = arrayOf("0.0.1")
}