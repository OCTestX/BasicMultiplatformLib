package io.github.octestx.basic.multiplatform.common.utils

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

@OptIn(ExperimentalSerializationApi::class)
val ojson = Json {
    this.prettyPrint = true
    this.isLenient = true
    this.allowTrailingComma = true
//    this.ignoreUnknownKeys = true
}

/**
 * 将json摊平，缩进使用///分割
 */
fun Json.flattenJsonElement(element: JsonElement, prefix: String = ""): Map<String, String> {
    return when (element) {
        is JsonObject -> element.entries.flatMap { (key, value) ->
            flattenJsonElement(value, if (prefix.isEmpty()) key else "$prefix///$key").map { it.key to it.value }
        }.toMap()
        is JsonArray -> element.indices.flatMap { index ->
            flattenJsonElement(element[index], if (prefix.isEmpty()) index.toString() else "$prefix///$index").map { it.key to it.value }
        }.toMap()
        else -> mapOf(prefix to element.toString())
    }
}