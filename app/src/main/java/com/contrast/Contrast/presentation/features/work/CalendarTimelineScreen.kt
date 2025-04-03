package com.contrast.Contrast.presentation.features.work

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import com.contrast.Contrast.presentation.components.calendar.CalendarEvent
import com.contrast.Contrast.presentation.components.calendar.CalendarScreen

import com.contrast.Contrast.presentation.components.calendar.VerticalCalendarDayView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTimelineScreen() {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    val sampleEvents = listOf(
        CalendarEvent(
            id = "1",
            title = "Dung",
            location = "Moscone West",
            startTime = "03/04/2025 08:00",
            endTime = "03/04/2025 10:00",
            color = Color(0xFF81C784),
            startTimeParsed = LocalDateTime.parse("03/04/2025 08:00", formatter),
            endTimeParsed = LocalDateTime.parse("03/04/2025 10:00", formatter)
        ),
        CalendarEvent(
            id = "2",
            title = "Phong",
            location = "Room 202",
            startTime = "03/04/2025 08:00",
            endTime = "03/04/2025 12:00",
            color = Color(0xFF64B5F6),
            startTimeParsed = LocalDateTime.parse("03/04/2025 08:00", formatter),
            endTimeParsed = LocalDateTime.parse("03/04/2025 12:00", formatter)
        ),  CalendarEvent(
            id = "7",
            title = "Hang",
            location = "Moscone West",
            startTime = "03/04/2025 20:00",
            endTime = "03/04/2025 21:00",
            color = Color(0xFF81C784),
            startTimeParsed = LocalDateTime.parse("03/04/2025 20:00", formatter),
            endTimeParsed = LocalDateTime.parse("03/04/2025 21:00", formatter)
        ),
        CalendarEvent(
            id = "8",
            title = "Dev Meeting",
            location = "Room 202",
            startTime = "03/04/2025 11:00",
            endTime = "03/04/2025 12:00",
            color = Color(0xFF64B5F6),
            startTimeParsed = LocalDateTime.parse("03/04/2025 11:00", formatter),
            endTimeParsed = LocalDateTime.parse("03/04/2025 12:00", formatter)
        )
            ,  CalendarEvent(
            id = "9",
            title = "WWDC",
            location = "Moscone West",
            startTime = "03/04/2025 13:00",
            endTime = "03/04/2025 15:00",
            color = Color(0xFF81C784),
            startTimeParsed = LocalDateTime.parse("03/04/2025 13:00", formatter),
            endTimeParsed = LocalDateTime.parse("03/04/2025 15:00", formatter)
        ),
        CalendarEvent(
            id = "10",
            title = "Dev Meeting",
            location = "Room 202",
            startTime = "03/04/2025 08:00",
            endTime = "04/04/2025 12:00",
            color = Color(0xFF64B5F6),
            startTimeParsed = LocalDateTime.parse("03/04/2025 08:00", formatter),
            endTimeParsed = LocalDateTime.parse("04/04/2025 12:00", formatter)
        )

    )

    CalendarScreen(

        events = sampleEvents,

        onEventClick = { event ->
            println("Clicked: ${event.title}")
        }
    )


}

