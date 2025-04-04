// TimelineDailyView.kt - Jetpack Compose (Updated with Auto Scroll + Tap/Long-Press Add Event)

package com.contrast.Contrast.presentation.components.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// Data

data class DailyCalendarEvent(
    val id: String,
    val title: String,
    val color: Color,
    val startTime: LocalTime,
    val endTime: LocalTime
)

interface TimelineDailyListener {
    fun onAddEvent(time: LocalTime)
    fun onLongPress(time: LocalTime)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineDailyView(
    events: Map<LocalDate, List<DailyCalendarEvent>>, // Group by day
    modifier: Modifier = Modifier,
    listener: TimelineDailyListener? = null
) {
    val hourHeight = 80.dp
    val userColumnWidth = 320.dp
    val currentWeekStart = remember { getStartOfWeek(LocalDate.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val daysOfWeek = remember(currentWeekStart) { (0..6).map { currentWeekStart.plusDays(it.toLong()) } }

    val nowTime = remember { mutableStateOf(LocalTime.now()) }
    LaunchedEffect(Unit) {
        while (true) {
            nowTime.value = LocalTime.now()
            delay(60_000)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Header
        val dateFormatter = DateTimeFormatter.ofPattern("dd")

// Cố định tiêu đề thứ
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            daysOfWeek.forEach { date ->
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date.dayOfWeek.getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale("vi")),
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }
        }

// Scroll phần ngày (hiển thị ngày trong tháng)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            daysOfWeek.forEach { date ->
                val isSelected = date == selectedDate
                Column(
                    modifier = Modifier
                        .width(48.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(if (isSelected) Color(0xFF1976D2) else Color.Transparent)
                        .clickable { selectedDate = date }
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = date.format(dateFormatter),
                        color = if (isSelected) Color.White else Color.Black,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // TimelineView
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount > 20) selectedDate = selectedDate.minusDays(1)
                        if (dragAmount < -20) selectedDate = selectedDate.plusDays(1)
                    }
                }
        ) {
            TimelineSingleDayContent(
                date = selectedDate,
                events = events[selectedDate].orEmpty(),
                now = nowTime.value,
                hourHeight = hourHeight,
                columnWidth = userColumnWidth,
                listener = listener
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineSingleDayContent(
    date: LocalDate,
    events: List<DailyCalendarEvent>,
    now: LocalTime,
    hourHeight: Dp,
    columnWidth: Dp,
    listener: TimelineDailyListener? = null
) {
    val scrollState = rememberScrollState()
    val totalHours = 24
    val coroutineScope = rememberCoroutineScope()

    // Auto scroll to now
    LaunchedEffect(Unit) {
        val offset = ((now.hour * 60 + now.minute) / 60f) * hourHeight.value
        scrollState.scrollTo(offset.toInt())
    }

    Box(
        Modifier
            .verticalScroll(scrollState)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val y = offset.y
                        val hour = (y / hourHeight.toPx()).toInt()
                        val minute = (((y % hourHeight.toPx()) / hourHeight.toPx()) * 60).toInt()
                        listener?.onAddEvent(LocalTime.of(hour.coerceIn(0, 23), minute.coerceIn(0, 59)))
                    },
                    onLongPress = { offset ->
                        val y = offset.y
                        val hour = (y / hourHeight.toPx()).toInt()
                        val minute = (((y % hourHeight.toPx()) / hourHeight.toPx()) * 60).toInt()
                        listener?.onLongPress(LocalTime.of(hour.coerceIn(0, 23), minute.coerceIn(0, 59)))
                    }
                )
            }
    ) {
        Box(Modifier.height(hourHeight * totalHours)) {
            // Hour Lines
            Column {
                repeat(totalHours) { hour ->
                    Box(Modifier.height(hourHeight)) {
                        Divider(
                            color = Color.LightGray,
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                        Text(
                            text = "%02d:00".format(hour),
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.TopStart).padding(start = 4.dp)
                        )
                    }
                }
            }

            // Now line
            if (date == LocalDate.now()) {
                val nowMinutes = now.hour * 60 + now.minute
                val nowOffset = (nowMinutes / 60f) * hourHeight.value

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .absoluteOffset(y = nowOffset.dp)
                        .zIndex(2f)
                ) {
                    drawLine(
                        color = Color.Red,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = 2f
                    )
                }
            }

            // Events
            val positioned = calculateDailyOverlaps(events)
            positioned.forEach { (event, column, totalCols) ->
                val startMinutes = event.startTime.toSecondOfDay() / 60
                val endMinutes = event.endTime.toSecondOfDay() / 60
                val topOffset = (startMinutes / 60f) * hourHeight.value
                val height = ((endMinutes - startMinutes) / 60f) * hourHeight.value
                val colWidth = if (totalCols == 1) columnWidth else columnWidth / totalCols
                val offsetX = if (totalCols == 1) 0.dp else colWidth * column

                Box(
                    modifier = Modifier
                        .absoluteOffset(x = offsetX, y = topOffset.dp)
                        .width(colWidth - 4.dp)
                        .padding(2.dp)
                        .height(height.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(event.color)
                        .zIndex(1f),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = event.title,
                        modifier = Modifier.padding(4.dp),
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateDailyOverlaps(events: List<DailyCalendarEvent>): List<Triple<DailyCalendarEvent, Int, Int>> {
    val sorted = events.sortedBy { it.startTime }
    val groups = mutableListOf<MutableList<DailyCalendarEvent>>()

    for (event in sorted) {
        var placed = false
        for (group in groups) {
            if (group.any { it.startTime < event.endTime && event.startTime < it.endTime }) {
                group.add(event)
                placed = true
                break
            }
        }
        if (!placed) groups.add(mutableListOf(event))
    }

    val result = mutableListOf<Triple<DailyCalendarEvent, Int, Int>>()
    for (group in groups) {
        val total = group.size
        group.sortedBy { it.startTime }.forEachIndexed { idx, e ->
            result.add(Triple(e, idx, total))
        }
    }
    return result
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun getStartOfWeek(today: LocalDate): LocalDate {
//    val diff = (today.dayOfWeek.value % 7) // Chủ Nhật là 0
//    return today.minusDays(diff.toLong())
//}
//@RequiresApi(Build.VERSION_CODES.O)
//fun getStartOfWeek(today: LocalDate): LocalDate {
//    val dayOfWeek = today.dayOfWeek
//    val diff = if (dayOfWeek == DayOfWeek.SUNDAY) 0 else dayOfWeek.value
//    return today.minusDays(diff.toLong())
//}

@RequiresApi(Build.VERSION_CODES.O)
fun getStartOfWeek(today: LocalDate): LocalDate {
    val dayOfWeek = today.dayOfWeek.value // Thứ Hai = 1, Chủ Nhật = 7
    return today.minusDays((dayOfWeek - 1).toLong()) // Trừ đi để về Thứ Hai
}
