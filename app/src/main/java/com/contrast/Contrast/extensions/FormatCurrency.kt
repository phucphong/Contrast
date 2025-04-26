package com.contrast.Contrast.extensions

fun Int.formatCurrency(): String {
    return "%,d".format(this).replace(',', '.')
}

fun Double.formatCurrency(): String {
    return "%,.0f đ".format(this).replace(',', '.')
}
fun Float.formatCurrency(): String {
    return "%,.0f đ".format(this).replace(',', '.')
}


fun Double.formatDouble(): String {
    return "%,.0f".format(this).replace(',', '.')
}


fun Float.formatFloat(): String {
    return "%,.0f".format(this).replace(',', '.')
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