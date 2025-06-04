package com.contrast.Contrast.extensions

import android.util.Log
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale


fun Int.formatCurrency(): String {
    return "%,d".format(this).replace(',', '.')
}
fun Double.formatCurrency(): String {
    return if (this < 1) {
        "%.2fđ".format(this).trimEnd('0').trimEnd('.') // giữ số nhỏ
    } else {
        "%,.0fđ".format(this).replace(',', '.')       // format lớn
    }
}

fun Float.formatCurrency(): String {
    return if (this < 1f) {
        "%.2fđ".format(this).trimEnd('0').trimEnd('.') // giữ số nhỏ
    } else {
        "%,.0fđ".format(this).replace(',', '.')       // format lớn
    }
}

fun Double.formatDouble(): String {
    return if (this < 1) {
        this.toString()
    } else {
        "%,.0f".format(this).replace(',', '.')
    }
}


fun Float.formatFloat(): String {
    return if (this < 1) {
        this.toString()
    } else {
        "%,.0f".format(this).replace(',', '.')
    }
}
fun formatDuration(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    val hours = totalSeconds / 3600

    return if (hours > 0)
        "%d:%02d:%02d".format(hours, minutes, seconds)
    else
        "%02d:%02d".format(minutes, seconds)
}

fun formatSizeInMB(sizeInBytes: Long): Double {
    return sizeInBytes / 1024.0 / 1024.0
}



fun formatCurrencyInput(input: String): String {
    return try {
        val raw = input.replace("[^\\d.,]".toRegex(), "")
        Log.e("CurrencyInput", "After cleaning: $raw")

        val normalized = raw.replace(",", ".")

        val number = normalized.toDoubleOrNull()
        if (number != null) {
            val symbols = DecimalFormatSymbols(Locale("vi", "VN")).apply {
                groupingSeparator = '.'
                decimalSeparator = ','
            }
            val formatter = DecimalFormat("#,###.##", symbols)
            formatter.format(number)
        } else {
            "" // hoặc trả về raw nếu bạn muốn hiển thị chuỗi chưa format
        }
    } catch (e: Exception) {
        ""
    }
}


fun String.toCleanDouble(): Double {
    return try {
        val clean = this.replace("[^\\d,]".toRegex(), "") // chỉ giữ số và dấu phẩy
        val normalized = clean.replace(".", "").replace(",", ".")
        normalized.toDoubleOrNull() ?: 0.0
    } catch (e: Exception) {
        0.0
    }
}

