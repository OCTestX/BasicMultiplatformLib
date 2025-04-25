package io.github.octestx.basic.multiplatform.common.utils

object Utils {
//    fun formatFileSize(size: Long): String {
//        if (size <= 0) return "0 B"
//
//        val units = listOf(
//            Triple("EB", 1152921504606846976L, 1),
//            Triple("PB", 1125899906842624L, 1),
//            Triple("TB", 1099511627776L, 2),
//            Triple("GB", 1073741824L, 2),
//            Triple("MB", 1048576L, 1),
//            Triple("KB", 1024L, 1),
//            Triple("B", 1L, 0)
//        )
//
//        for ((unit, divisor, decimals) in units) {
//            if (size >= divisor) {
//                val value = size.toDouble() / divisor.toDouble()
//                return formatNumberWithDecimals(value, decimals) + " $unit"
//            }
//        }
//        return "$size B"
//    }
//
//    // 手动实现小数格式化
//    private fun formatNumberWithDecimals(value: Double, decimals: Int): String {
//        if (decimals == 0 || value % 1 == 0.0) {
//            return value.toInt().toString() // 整数直接返回
//        }
//
//        // 四舍五入到指定小数位
//        val factor = 10.0.pow(decimals)
//        val rounded = (value * factor).roundToInt() / factor
//
//        // 分离整数和小数部分
//        val integerPart = rounded.toInt()
//        val decimalPart = ((rounded - integerPart) * factor).roundToInt()
//
//        // 处理小数末尾补零
//        return buildString {
//            append(integerPart)
//            if (decimals > 0) {
//                append(".")
//                append(decimalPart.toString().padStart(decimals, '0'))
//            }
//        }
//    }
}