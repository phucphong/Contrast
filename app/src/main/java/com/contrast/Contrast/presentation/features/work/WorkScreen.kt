
package com.contrast.Contrast.presentation.features.work

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.*


import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

data class CalendarDayData(
    val date: LocalDate,
    val hasEvent: Boolean = false
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkScreen() {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    val eventDates = remember {
        val today = LocalDate.now()
        listOf(
            today.withDayOfMonth(3),
            today.withDayOfMonth(8),
            today.withDayOfMonth(14),
            today.withDayOfMonth(16),
            today.withDayOfMonth(24),
            today.withDayOfMonth(28),
            today.withDayOfMonth(today.lengthOfMonth())
        )
    }


    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

//    HorizontalCalendar(
//        eventDates = eventDates,
//        onDateSelected = { selectedDate ->
//            // Xử lý khi một ngày được chọn
//        }
//    )

//    CalendarTimelineScreen()

    CalendarTimelineScreen(

    )

}
