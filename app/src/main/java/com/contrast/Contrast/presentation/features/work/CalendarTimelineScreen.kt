package com.contrast.Contrast.presentation.features.work

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import com.contrast.Contrast.presentation.components.calendar.CalendarEvent
import com.contrast.Contrast.presentation.components.calendar.CalendarScreen
import com.contrast.Contrast.presentation.components.calendarMore.CalendarScreenMore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTimelineScreen() {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    val sampleEvents = listOf(
        CalendarEvent(
            id = "1",
            title = "Dev 1",
            location = "Moscone West",
            color = Color(0xFF81C784),
            startTime = LocalDateTime.parse("04/04/2025 00:00", formatter),
            endTime = LocalDateTime.parse("04/04/2025 10:00", formatter)
        ),
        CalendarEvent(
            id = "2",
            title = "Dev 2",
            location = "Room 202",
            color = Color(0xFF64B5F6),
            startTime = LocalDateTime.parse("04/04/2025 00:00", formatter),
            endTime = LocalDateTime.parse("04/04/2025 12:00", formatter)
        ),  CalendarEvent(
            id = "7",
            title = "Dev 7",
            location = "Moscone West",
            color = Color(0xFF81C784),
            startTime = LocalDateTime.parse("04/04/2025 00:00", formatter),
            endTime = LocalDateTime.parse("04/04/2025 21:00", formatter)
        ),
        CalendarEvent(
            id = "8",
            title = "Dev 8",
            location = "Room 202",
            color = Color(0xFF64B5F6),
            startTime = LocalDateTime.parse("04/04/2025 00:00", formatter),
            endTime = LocalDateTime.parse("04/04/2025 12:00", formatter)
        )
            ,  CalendarEvent(
            id = "9",
            title = "Dev 9",
            location = "Moscone West",
            color = Color(0xFF81C784),
            startTime = LocalDateTime.parse("04/04/2025 00:00", formatter),
            endTime = LocalDateTime.parse("04/04/2025 15:00", formatter)
        ),
        CalendarEvent(
            id = "10",
            title = "Dev 10",
            location = "Room 202",
            color = Color(0xFF64B5F6),
            startTime = LocalDateTime.parse("04/04/2025 00:00", formatter),
            endTime = LocalDateTime.parse("05/04/2025 12:00", formatter)
        )

    )
//    CalendarScreen(
//        events = sampleEvents,
//        onEventClick = { event ->
//            println("Clicked: ${event.title}")
//        }
//    )
    CalendarScreenMore(
        events = sampleEvents,
        onEventClick = { event ->
            println("Clicked: ${event.title}")
        }
    )


}

