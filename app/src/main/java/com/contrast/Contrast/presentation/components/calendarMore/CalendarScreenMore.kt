package com.contrast.Contrast.presentation.components.calendarMore

import com.contrast.Contrast.presentation.components.calendar.CalendarEvent
import com.contrast.Contrast.presentation.components.calendar.DailyCalendarEvent




import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreenMore(
    events: List<CalendarEvent>,
    onEventClick: (CalendarEvent) -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }


    // Swipe để đổi ngày
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    if (dragAmount > 0) selectedDate = selectedDate.minusDays(1)
                    else if (dragAmount < 0) selectedDate = selectedDate.plusDays(1)
                }
            }
    ) {
        Column {

            val today = LocalDate.now()
            val sampleEvents = mapOf(
                today to listOf( // <-- dùng hôm nay

                    DailyCalendarEvent(
                        id = "1",
                        title = "Ngày 20/10 là ngày nhà giáo Việt Nam",
                        color = Color(0xFF81C784),
                        startTime = LocalDateTime.of(today, LocalTime.of(0, 0)),
                        endTime = LocalDateTime.of(today, LocalTime.of(23, 59))
                    ),  DailyCalendarEvent(
                        id = "1",
                        title = "Ngày 20/10 là ngày nhà giáo Việt Nam",
                        color = Color(0xFF81C784),
                        startTime = LocalDateTime.of(today, LocalTime.of(0, 0)),
                        endTime = LocalDateTime.of(today, LocalTime.of(23, 59))
                    ),  DailyCalendarEvent(
                        id = "1",
                        title = "Ngày 20/10 là ngày nhà giáo Việt Nam",
                        color = Color(0xFF81C784),
                        startTime = LocalDateTime.of(today, LocalTime.of(0, 0)),
                        endTime = LocalDateTime.of(today, LocalTime.of(23, 59))
                    ),
                    DailyCalendarEvent("1", "CV 1.0", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(8, 0)), LocalDateTime.of(today, LocalTime.of(8, 59))),
                    DailyCalendarEvent("1", "CV 1.1", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(8, 0)), LocalDateTime.of(today, LocalTime.of(8, 59))),
                    DailyCalendarEvent("1", "CV 1.2", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(8, 0)), LocalDateTime.of(today, LocalTime.of(8, 59))),
                    DailyCalendarEvent("1", "CV 1.3", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(8, 0)), LocalDateTime.of(today, LocalTime.of(8, 59))),
                    DailyCalendarEvent("1", "CV 14", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(8, 0)), LocalDateTime.of(today, LocalTime.of(8, 59))),
                    DailyCalendarEvent("1", "CV 2.0", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(9, 0)), LocalDateTime.of(today, LocalTime.of(9, 59))),
                    DailyCalendarEvent("1", "CV 24", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(9, 0)), LocalDateTime.of(today, LocalTime.of(9, 59))),
                    DailyCalendarEvent("1", "CV 3.0", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(10, 0)), LocalDateTime.of(today, LocalTime.of(10, 59))),
                    DailyCalendarEvent("1", "CV 3.0", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(8, 30)), LocalDateTime.of(today, LocalTime.of(10, 30))),
                    DailyCalendarEvent("1", "CV 34", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(10, 0)), LocalDateTime.of(today, LocalTime.of(10, 59))),
                    DailyCalendarEvent("1", "CV 5", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(12, 0)), LocalDateTime.of(today, LocalTime.of(16, 59))),
                    DailyCalendarEvent("1", "CV 6", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(12, 0)), LocalDateTime.of(today, LocalTime.of(16, 59))),
                    DailyCalendarEvent("1", "CV 7", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(12, 0)), LocalDateTime.of(today, LocalTime.of(16, 59))),
                    DailyCalendarEvent("1", "CV 8", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(12, 0)), LocalDateTime.of(today, LocalTime.of(16, 59))),
                    DailyCalendarEvent("1", "CV 9", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(12, 0)), LocalDateTime.of(today, LocalTime.of(16, 59))),
                    DailyCalendarEvent("1", "CV 9", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(18, 0)), LocalDateTime.of(today, LocalTime.of(19, 59))),
                    DailyCalendarEvent("1", "CV 9", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(11, 10)), LocalDateTime.of(today, LocalTime.of(11, 55)))



                )
            )




            TimelineDailyViewMore(
                events = sampleEvents,
                listener = object : TimelineDailyListener {
                    override fun onAddEvent(time: LocalTime) {
                        println("Thêm sự kiện lúc: $time")
                    }

                    override fun onLongPress(time: LocalTime) {
                        println("Long press lúc: $time")
                    }

                    override fun onClickEvent(event: DailyCalendarEvent) {
                        println("Click sự kiện: ${event.title} (${event.startTime} - ${event.endTime})")
                    }
                },
                modifier = Modifier.fillMaxSize()
            )


        }
    }
}



