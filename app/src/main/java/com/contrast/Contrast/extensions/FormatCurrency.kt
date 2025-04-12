package com.contrast.Contrast.extensions

fun Int.formatCurrency(): String {
    return "%,d đ".format(this).replace(',', '.')
}

fun Double.formatCurrency(): String {
    return "%,.0f đ".format(this).replace(',', '.')
}
