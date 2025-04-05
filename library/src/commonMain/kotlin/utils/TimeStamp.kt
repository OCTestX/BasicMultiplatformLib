package io.github.kotlin.fibonacci.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object TimeStamp {
    val current get() = System.currentTimeMillis()

    fun formatMilliseconds(milliseconds: Long): String {
        val seconds = (milliseconds / 1000) % 60
        val minutes = ((milliseconds / (1000 * 60)) % 60)
        val hours = (milliseconds / (1000 * 60 * 60))

        return when {
            hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
            minutes > 0 -> String.format("%d:%02d", minutes, seconds)
            else -> String.format("%d", seconds)
        }
    }

    fun formatTimestampToChinese(timestamp: Long): String {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.year}年" +
                "${localDateTime.monthNumber}月" +
                "${localDateTime.dayOfMonth}日-" +
                "${localDateTime.hour.toString().padStart(2, '0')}:" +
                "${localDateTime.minute.toString().padStart(2, '0')}:" +
                localDateTime.second.toString().padStart(2, '0')
    }
}