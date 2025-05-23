package io.github.octestx.basic.multiplatform.common.utils

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
sealed class AIResult<T> {
    @Serializable
    data class Success<T>(val result: T) : AIResult<T>()

    @Serializable
    data class Failed<T>(
        @Contextual // 添加上下文序列化注解
        val error: Exception,
        val type: AIErrorType
    ) : AIResult<T>()
}

enum class AIErrorType(val message: String) {
    INVALID_API_KEY("无效的API密钥"),
    INVALID_MODEL("无效的模型"),
    API_QUOTA_EXHAUSTED("API调用过量（无剩余Token）"),
    API_RATE_LIMIT_CONCURRENCY("API调用限制（并发过多）"),
    API_RATE_LIMIT_TOKEN("API调用限制（单位时间内Token限制）"),
    SENSITIVE_INFO("敏感信息"),
    REQUEST_TIMEOUT("请求超时"),
    REQUEST_INTERRUPTED("请求中断"),
    UNKNOWN("未知错误")
}