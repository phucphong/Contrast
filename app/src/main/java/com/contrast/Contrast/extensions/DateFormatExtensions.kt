package com.contrast.Contrast.extensions


fun formatDateYYYYMMDD(day: Int, month: Int, year: Int): String {
    return "%04d-%02d-%02d".format(year, month, day)
}

fun formatToYYYYMMDD(input: String): String {
    return try {
        val inputFormat = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.getDefault())
        val outputFormat = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val date = inputFormat.parse(input)
        outputFormat.format(date ?: return "")
    } catch (e: Exception) {
        ""
    }
}

fun formatToDDMYYYYHHMM(input: String): String {
    return try {
        val inputFormat = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.getDefault())
        val outputFormat = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
        val date = inputFormat.parse(input)
        outputFormat.format(date ?: return "")
    } catch (e: Exception) {
        ""
    }
}
fun formatToDDMYYYY(input: String): String {
    return try {
        val inputFormat = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss", java.util.Locale.getDefault())
        val outputFormat = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        val date = inputFormat.parse(input)
        outputFormat.format(date ?: return "")
    } catch (e: Exception) {
        ""
    }
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

