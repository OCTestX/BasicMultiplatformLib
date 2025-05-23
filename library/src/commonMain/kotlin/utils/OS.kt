package io.github.octestx.basic.multiplatform.common.utils

object OS {
    // 定义操作系统类型的枚举
    enum class OperatingSystem {
        WIN, MACOS, LINUX, OTHER
    }

    // 获取当前操作系统
    val currentOS by lazy {
        val osName = System.getProperty("os.name").lowercase()
        when {
            "win" in osName -> OperatingSystem.WIN
            listOf("mac", "darwin").any { it in osName } -> OperatingSystem.MACOS
            listOf("nix", "nux", "linux").any { it in osName } -> OperatingSystem.LINUX
            else -> OperatingSystem.OTHER
        }
    }
}