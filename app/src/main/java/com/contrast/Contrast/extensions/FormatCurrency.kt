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
