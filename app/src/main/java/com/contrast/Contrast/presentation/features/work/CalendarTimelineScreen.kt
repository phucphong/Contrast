package com.contrast.Contrast.presentation.features.work

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import com.contrast.Contrast.presentation.components.calendar.CalendarEvent
import com.contrast.Contrast.presentation.components.calendar.TimelineDayView
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarTimelineScreen() {
    val sampleEvents = listOf(
        CalendarEvent(
            id = "1",
            title = "WWDC",
            location = "Moscone West",
            startTime = LocalTime.of(10, 0),
            endTime = LocalTime.of(12, 0),
            color = Color(0xFF81C784)
        ),
        CalendarEvent(
            id = "2",
            title = "Dev Meeting",
            location = "Room 202",
            startTime = LocalTime.of(14, 0),
            endTime = LocalTime.of(15, 0),
            color = Color(0xFF64B5F6)
        ),
        CalendarEvent(
            id = "3",
            title = "Night Coding",
            location = "Worldwide",
            startTime = LocalTime.of(21, 49),
            endTime = LocalTime.of(22, 15),
            color = Color(0xFFE57373)
        )
    )

    TimelineDayView (
        events = sampleEvents,
        modifier = Modifier.fillMaxSize(),
        onEventClick = { event: CalendarEvent ->
            println("Clicked event: ${event.title}")
        }

    )
}

