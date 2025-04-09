package com.contrast.Contrast.extensions


fun formatDateYYYYMMDD(day: Int, month: Int, year: Int): String {
    return "%04d-%02d-%02d".format(year, month, day)
}


fun convertDateToYYYYMMDD(dateStr: String): String {
    return try {
        val parts = dateStr.split("/")
        if (parts.size == 3) {
            val day = parts[0].padStart(2, '0')
            val month = parts[1].padStart(2, '0')
            val year = parts[2]
            "$year/$month/$day"
        } else {
            ""
        }
    } catch (e: Exception) {
        ""
    }
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

