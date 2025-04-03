package com.contrast.Contrast.extensions

fun Int.formatCurrency(): String {
    return "%,d đ".format(this).replace(',', '.')
}
