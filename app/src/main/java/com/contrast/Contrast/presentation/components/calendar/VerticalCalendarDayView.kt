package com.contrast.Contrast.presentation.components.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Model mở rộng để chứa vị trí cột của sự kiện
data class PositionedEvent(
    val event: CalendarEvent,
    val column: Int,
    val totalColumns: Int
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VerticalCalendarDayView(
    date: LocalDate,
    events: List<CalendarEvent>,
    modifier: Modifier = Modifier,
    onEventClick: (CalendarEvent) -> Unit
) {
    val hourHeight = 80.dp
    val totalHours = 24
    val listState = rememberLazyListState()

    val filteredEvents = remember(events, date) {
        events.filter { event ->
            val start = event.startTimeParsed
            val end = event.endTimeParsed
            !start.toLocalDate().isAfter(date) && !end.toLocalDate().isBefore(date)
        }
    }

    val positionedEvents = remember(filteredEvents) {
        calculateOverlaps(filteredEvents)
    }

    val isToday = date == LocalDate.now()
    val nowTime = remember { mutableStateOf(LocalDateTime.now()) }

    // Update current time every minute
    LaunchedEffect(Unit) {
        while (true) {
            nowTime.value = LocalDateTime.now()
            delay(60_000)
        }
    }

    // Auto scroll to current time
    LaunchedEffect(Unit) {
        if (isToday) {
            val now = nowTime.value
            val nowMinutes = now.hour * 60 + now.minute
            val offset = ((nowMinutes / 60f) * hourHeight.value).toInt()
            listState.scrollToItem(0, offset)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Time Column + Lines
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(totalHours) { hour ->
                Box(modifier = Modifier
                    .height(hourHeight)
                    .fillMaxWidth()) {
                    Text(
                        text = "%02d:00".format(hour),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 8.dp),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Divider(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(start = 60.dp),
                        color = Color.LightGray,
                        thickness = 0.5.dp
                    )
                }
            }
        }

        // Layer vẽ event block đúng vị trí
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = -listState.firstVisibleItemScrollOffset +
                            listState.firstVisibleItemIndex * hourHeight.toPx()
                }
        ) {
            positionedEvents.forEach { positioned ->
                val event = positioned.event
                val column = positioned.column
                val totalCols = positioned.totalColumns

                val start = event.startTimeParsed
                val end = event.endTimeParsed

                val dayStart = date.atStartOfDay()
                val dayEnd = date.plusDays(1).atStartOfDay()

                val startClamped = if (start.isBefore(dayStart)) dayStart else start
                val endClamped = if (end.isAfter(dayEnd)) dayEnd else end

                val startMinutes = startClamped.toLocalTime().toSecondOfDay() / 60
                val endMinutes = endClamped.toLocalTime().toSecondOfDay() / 60

                val topOffset = (startMinutes / 60f) * hourHeight.value
                val height = ((endMinutes - startMinutes) / 60f) * hourHeight.value

                val contentWidth = 320.dp
                val columnWidth = contentWidth / totalCols
                val offsetX = 68.dp + columnWidth * column

                Box(
                    modifier = Modifier
                        .absoluteOffset(y = topOffset.dp, x = offsetX)
                        .width(columnWidth - 4.dp)
                        .height(height.dp.coerceAtLeast(36.dp))
                        .clip(RoundedCornerShape(6.dp))
                        .background(event.color)
                        .clickable { onEventClick(event) }
                        .zIndex(1f)
                        .padding(4.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Column {
                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White
                        )
                        Text(
                            text = "${event.startTimeParsed.toLocalTime()} - ${event.endTimeParsed.toLocalTime()}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            // Now line
            if (isToday) {
                val now = nowTime.value.toLocalTime()
                val nowMinutes = now.hour * 60 + now.minute
                val nowOffset = (nowMinutes / 60f) * hourHeight.value

                Box(
                    modifier = Modifier
                        .absoluteOffset(y = nowOffset.dp)
                        .fillMaxWidth()
                        .zIndex(2f)
                        .padding(start = 60.dp, end = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = now.format(DateTimeFormatter.ofPattern("HH:mm")),
                            color = Color.Red,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.width(45.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color.Red)
                        )
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .padding(start = 4.dp)
                                .weight(1f),
                            color = Color.Red,
                            thickness = 1.dp
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateOverlaps(events: List<CalendarEvent>): List<PositionedEvent> {
    val sorted = events.sortedBy { it.startTimeParsed }
    val groups = mutableListOf<List<CalendarEvent>>()

    for (event in sorted) {
        var added = false
        for (group in groups) {
            if (group.any { isOverlapping(it, event) }) {
                (group as MutableList).add(event)
                added = true
                break
            }
        }
        if (!added) {
            groups.add(mutableListOf(event))
        }
    }

    val positioned = mutableListOf<PositionedEvent>()

    for (group in groups) {
        val totalCols = group.size
        group.sortedBy { it.startTimeParsed }.forEachIndexed { index, event ->
            positioned.add(
                PositionedEvent(
                    event = event,
                    column = index,
                    totalColumns = totalCols
                )
            )
        }
    }

    return positioned
}

@RequiresApi(Build.VERSION_CODES.O)
fun isOverlapping(a: CalendarEvent, b: CalendarEvent): Boolean {
    return a.startTimeParsed < b.endTimeParsed && b.startTimeParsed < a.endTimeParsed
}