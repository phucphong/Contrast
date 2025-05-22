package com.contrast.Contrast.extensions


import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
object DateUtils {

    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // Ngày hôm nay
    fun today(): String = LocalDate.now().format(formatter)

    // 7 ngày trước
    fun sevenDaysAgo(): String = LocalDate.now().minusDays(7).format(formatter)

    // 30 ngày trước
    fun thirtyDaysAgo(): String = LocalDate.now().minusDays(30).format(formatter)

    // Đầu tháng hiện tại
    fun firstDayOfThisMonth(): String = LocalDate.now().withDayOfMonth(1).format(formatter)

    // Cuối tháng hiện tại
    fun lastDayOfThisMonth(): String {
        val now = LocalDate.now()
        return YearMonth.of(now.year, now.month).atEndOfMonth().format(formatter)
    }

    // Đầu tháng trước
    fun firstDayOfLastMonth(): String {
        val lastMonth = LocalDate.now().minusMonths(1)
        return lastMonth.withDayOfMonth(1).format(formatter)
    }

    // Cuối tháng trước
    fun lastDayOfLastMonth(): String {
        val lastMonth = LocalDate.now().minusMonths(1)
        return YearMonth.of(lastMonth.year, lastMonth.month).atEndOfMonth().format(formatter)
    }

    // Đầu quý hiện tại
    fun firstDayOfThisQuarter(): String {
        val now = LocalDate.now()
        val startMonth = when (now.monthValue) {
            in 1..3 -> Month.JANUARY
            in 4..6 -> Month.APRIL
            in 7..9 -> Month.JULY
            else -> Month.OCTOBER
        }
        return LocalDate.of(now.year, startMonth, 1).format(formatter)
    }

    // Cuối quý hiện tại
    fun lastDayOfThisQuarter(): String {
        val now = LocalDate.now()
        val endMonth = when (now.monthValue) {
            in 1..3 -> Month.MARCH
            in 4..6 -> Month.JUNE
            in 7..9 -> Month.SEPTEMBER
            else -> Month.DECEMBER
        }
        return YearMonth.of(now.year, endMonth).atEndOfMonth().format(formatter)
    }

    // Đầu năm hiện tại
    fun firstDayOfThisYear(): String = LocalDate.of(LocalDate.now().year, Month.JANUARY, 1).format(formatter)

    // Cuối năm hiện tại
    fun lastDayOfThisYear(): String = LocalDate.of(LocalDate.now().year, Month.DECEMBER, 31).format(formatter)
    fun LocalDate.format(pattern: String): String =
        this.format(DateTimeFormatter.ofPattern(pattern))
    fun getCurrentTimeLabel(type: String, currentDate: LocalDate = LocalDate.now()): String {
        return when (type.uppercase()) {
            "WEEK" -> {
                val weekOfYear = currentDate.get(WeekFields.of(Locale.getDefault()).weekOfYear())
                "Tuần $weekOfYear"
            }

            "MONTH" -> {
                val month = currentDate.monthValue
                "Tháng $month"
            }

            "QUARTER" -> {
                val quarter = ((currentDate.monthValue - 1) / 3) + 1
                "Quý $quarter"
            }

            "YEAR" -> {
                val year = currentDate.year
                "Năm $year"
            }

            else -> ""
        }
    }

    fun getStartAndEndDate(type: String): Pair<String, String> {
        val today = LocalDate.now()
        return when (type) {
            "DAY" -> {
                val formatted = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                formatted to formatted
            }
            "WEEK" -> {
                val start = today.with(DayOfWeek.MONDAY)
                val end = today.with(DayOfWeek.SUNDAY)
                start.format("dd/MM/yyyy") to end.format("dd/MM/yyyy")
            }
            "MONTH" -> {
                val start = today.withDayOfMonth(1)
                val end = today.withDayOfMonth(today.lengthOfMonth())
                start.format("dd/MM/yyyy") to end.format("dd/MM/yyyy")
            }
            "QUARTER" -> {
                val month = today.monthValue
                val quarterStartMonth = ((month - 1) / 3) * 3 + 1
                val start = LocalDate.of(today.year, quarterStartMonth, 1)
                val end = start.plusMonths(2).withDayOfMonth(start.plusMonths(2).lengthOfMonth())
                start.format("dd/MM/yyyy") to end.format("dd/MM/yyyy")
            }
            "YEAR" -> {
                val start = LocalDate.of(today.year, 1, 1)
                val end = LocalDate.of(today.year, 12, 31)
                start.format("dd/MM/yyyy") to end.format("dd/MM/yyyy")
            }
            else -> today.format("dd/MM/yyyy") to today.format("dd/MM/yyyy")
        }
    }

}
