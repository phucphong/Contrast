package com.contrast.Contrast.extensions


fun formatDateYYYYMMDD(day: Int, month: Int, year: Int): String {
    return "%04d-%02d-%02d".format(year, month, day)
}




fun formatDateTimeDDMMYYYY(day: Int, month: Int, year: Int): String {
    return String.format("%02d/%02d/%04d", day, month, year)
}
fun formatDateTimeDDMMYYYY_HHMM(
    day: Int,
    month: Int,
    year: Int,
    hour: Int,
    minute: Int
): String {
    return "%02d/%02d/%04d %02d:%02d".format(day, month, year, hour, minute)
}


fun formatDateTimeYYYYMMDD_HHMM(
    day: Int,
    month: Int,
    year: Int,
    hour: Int,
    minute: Int
): String {
    return "%04d-%02d-%02d %02d:%02d".format(year, month, day, hour, minute)
}

