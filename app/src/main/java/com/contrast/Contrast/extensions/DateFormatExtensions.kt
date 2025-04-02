package com.contrast.Contrast.extensions


fun formatDateYYYYMMDD(day: Int, month: Int, year: Int): String {
    return "%04d-%02d-%02d".format(year, month + 1, day)
}

fun formatDateDDMMYYYY(day: Int, month: Int, year: Int): String {
    return "%02d/%02d/%04d".format(day, month + 1, year)
}
