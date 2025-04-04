package com.contrast.Contrast.presentation.components.calendar


import android.os.Build
import android.util.Log
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

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
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
//            DateHeaderBar(
//                selectedDate = selectedDate,
//                onDateSelected = { selectedDate = it }
//            )
//
//
//
//
//
////            AllDayEventsView(events = selectedDate, onEventClick = onEventClick)
//            VerticalCalendarDayView(
//                date = selectedDate,
//                events = events,
//                listener = timelineListener,
//                modifier = Modifier.fillMaxSize()
//            )


//            val users = listOf("T2", "T3", "T4")
//            val events = listOf(
//                MultiUserEvent("T2", "Test A", Color(0xFF81C784), LocalTime.of(1, 0), LocalTime.of(3, 0)),
//                MultiUserEvent("T2", "Test B", Color(0xFF81C784), LocalTime.of(2, 0), LocalTime.of(3, 0)),
//                MultiUserEvent("T2", "Test C", Color(0xFF81C784), LocalTime.of(0, 0), LocalTime.of(23, 59)),
//                MultiUserEvent("T2", "Test D", Color(0xFF81C784), LocalTime.of(0, 0), LocalTime.of(23, 59)),
//                MultiUserEvent("T2", "Test E", Color(0xFF81C784), LocalTime.of(0, 0), LocalTime.of(23, 59)),
//                MultiUserEvent("T2", "Test F", Color(0xFF81C784), LocalTime.of(0, 0), LocalTime.of(23, 59)),
//                MultiUserEvent("T3", "Họp", Color(0xFF64B5F6), LocalTime.of(8, 0), LocalTime.of(10, 0))
//            )
//
//            TimelineWeekView(
//                users = users,
//                date = LocalDate.now(),
//                events = events,
//                listener = object : TimelineWeekViewListener {
//                    override fun onTapEvent(event: MultiUserEvent) {
//                        Log.d("Tap", event.title)
//                    }
//
//                    override fun onLongPressEvent(event: MultiUserEvent) {
//                        Log.d("Long", event.title)
//                    }
//                }
//            )


            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

            val today = LocalDate.now()
            val sampleEvents = mapOf(
                today to listOf( // <-- dùng hôm nay
                    DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 1",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 2",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 3",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 4",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 5",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 6",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 7",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 8",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 9",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 10",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 1",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(9, 0),
                        endTime = LocalTime.of(23, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 0002",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(6, 0),
                        endTime = LocalTime.of(9, 59)
                    ), DailyCalendarEvent(
                        id = "1",
                        title = "Cuộc họp 0005",
                        color = Color(0xFF81C784),
                        startTime = LocalTime.of(10, 0),
                        endTime = LocalTime.of(12, 59)
                    )

                )
            )


            val listener = remember { MyTimelineListener() }

            TimelineDailyView(
                events = sampleEvents,
                listener = listener,
                modifier = Modifier.fillMaxSize()
            )


        }
    }
}

class MyTimelineListener : TimelineDailyListener {
    override fun onAddEvent(time: LocalTime) {
        println("Tapped to add event at: $time")
        // Show dialog tạo event ở đây
    }

    override fun onLongPress(time: LocalTime) {
        println("Long-pressed at: $time")
        // Show context menu hoặc bottom sheet
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateHeaderBar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    val startOfWeek = today.minusDays(today.dayOfWeek.value.toLong() - 1)
    val dates = (0..6).map { startOfWeek.plusDays(it.toLong()) }
    val formatter = DateTimeFormatter.ofPattern("EEE")

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dates) { date ->
            val isSelected = date == selectedDate
            val isToday = date == today

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(48.dp)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                        else Color.Transparent,
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable { onDateSelected(date) }
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = formatter.format(date),
                    fontSize = 12.sp,
                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = date.dayOfMonth.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onBackground,

                    )
            }
        }
    }
}
