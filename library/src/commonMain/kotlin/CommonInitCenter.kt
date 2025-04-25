package io.github.octestx.basic.multiplatform.common

import ca.gosyer.appdirs.AppDirs
import io.github.octestx.basic.multiplatform.common.logger.OLogger
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import java.io.File

lateinit var appDirs: AppDirs private set
internal object CommonInitCenter {
    private var initialized = false
    // 延迟加载配置，优先使用注入的实例
    inline fun init(platformInit: () -> Unit) {
        if (initialized) return
        //TODO INIT
        appDirs = get(AppDirs::class.java)
        OLogger.init()
        platformInit()
        initialized = true
    }
}
class BasicMultiplatformConfigModule() {
    var appDir: AppDirs? = null
    fun asModule() = module {
        val tmp = appDir ?: throw ExceptionInInitializerError("need config appDir")
        single<AppDirs> { tmp }
    }
    fun configInnerAppDir(parentDir: File) {
        appDir = object : AppDirs {
            override fun getSharedDir(): String = File(parentDir, "shared").absolutePath
            override fun getSiteConfigDir(multiPath: Boolean): String = File(parentDir, "siteConfig").absolutePath
            override fun getSiteDataDir(multiPath: Boolean): String = File(parentDir, "siteData").absolutePath
            override fun getUserCacheDir(): String = File(parentDir, "cache").absolutePath
            override fun getUserConfigDir(roaming: Boolean): String = File(parentDir, "config").absolutePath
            override fun getUserDataDir(roaming: Boolean): String = File(parentDir, "data").absolutePath
            override fun getUserLogDir(): String = File(parentDir, "logs").absolutePath
        }
    }
}