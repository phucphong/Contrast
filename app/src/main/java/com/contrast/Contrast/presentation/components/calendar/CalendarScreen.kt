package com.contrast.Contrast.presentation.components.calendar


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
import com.contrast.Contrast.presentation.components.calendar.VerticalCalendarDayView
import com.contrast.Contrast.presentation.components.calendar.CalendarEvent
import java.time.DayOfWeek
import java.time.LocalDate
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
            DateHeaderBar(
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )

            VerticalCalendarDayView(
                date = selectedDate,
                events = events,
                modifier = Modifier.weight(1f),
                onEventClick = onEventClick
            )
        }
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
