package io.github.octestx.basic.multiplatform.common.exceptions

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

// 自定义 Exception 序列化器
object ExceptionSerializer : KSerializer<Exception> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Exception", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Exception) {
        encoder.encodeString(value.stackTraceToString())
    }

    override fun deserialize(decoder: Decoder): Exception {
        return Exception(decoder.decodeString())
    }
}