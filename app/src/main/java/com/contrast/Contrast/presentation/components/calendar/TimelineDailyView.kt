// TimelineDailyViewMore.kt - Compose CalendarKit-style (fix che giờ)
package com.contrast.Contrast.presentation.components.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.squareup.moshi.Json
import java.time.LocalDateTime
// Data

data class DailyCalendarEvent(
    val id: String,
    val title: String,
    val color: Color,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)



//data class DailyCalendarEvent(
//    @Json(name = "id") val id: Long,
//    @Json(name = "loaicongviec") val type: Long,
//    @Json(name = "mauloaicongviec") val color: Color,
//    @Json(name = "tieude") val title: String,
//    @Json(name = "mauloaicongviec") val colorHex: String,
//    @Json(name = "tugio") val startTime: LocalDateTime,
//    @Json(name = "dengio") val endTime: LocalDateTime
//)


interface TimelineDailyListener {
    fun onAddEvent(time: LocalTime)
    fun onLongPress(time: LocalTime)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineDailyView(
    events: Map<LocalDate, List<DailyCalendarEvent>>,
    modifier: Modifier = Modifier,
    listener: TimelineDailyListener? = null
) {
    val hourHeight = 80.dp
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
        val dateFormatter = DateTimeFormatter.ofPattern("dd")

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            daysOfWeek.forEach { date ->
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(
                        text = date.dayOfWeek.name.take(2),
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
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

        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        TimelineSingleDayContent(
            date = selectedDate,
            events = events[selectedDate].orEmpty(),
            now = nowTime.value,
            hourHeight = hourHeight,
            screenWidth = screenWidth,
            listener = listener
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineSingleDayContent(
    date: LocalDate,
    events: List<DailyCalendarEvent>,
    now: LocalTime,
    hourHeight: Dp,
    screenWidth: Dp,
    listener: TimelineDailyListener? = null
) {
    val scrollState = rememberScrollState()
    val totalHours = 24

    LaunchedEffect(Unit) {
        val offset = ((now.hour * 60 + now.minute) / 60f) * hourHeight.value
        scrollState.scrollTo(offset.toInt())
    }

    val groups = calculateOverlapsByGroup(events)

    Row(modifier = Modifier.fillMaxSize()) {
        // Left Hour Column
        Column(
            modifier = Modifier.width(60.dp).verticalScroll(scrollState)
        ) {
            repeat(totalHours) { hour ->
                Box(
                    modifier = Modifier.height(hourHeight).padding(start = 4.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text("%02d:00".format(hour), fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        // Main Timeline Content
        Box(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState).pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        val hour = (offset.y / hourHeight.toPx()).toInt()
                        val minute = (((offset.y % hourHeight.toPx()) / hourHeight.toPx()) * 60).toInt()
                        listener?.onAddEvent(LocalTime.of(hour, minute))
                    },
                    onLongPress = { offset ->
                        val hour = (offset.y / hourHeight.toPx()).toInt()
                        val minute = (((offset.y % hourHeight.toPx()) / hourHeight.toPx()) * 60).toInt()
                        listener?.onLongPress(LocalTime.of(hour, minute))
                    }
                )
            }
        ) {
            Box(Modifier.height(hourHeight * totalHours)) {
                // Timeline Grid Lines (cover full width incl. time col)
                Canvas(modifier = Modifier.fillMaxSize().zIndex(0f)) {
                    val fullWidth = size.width
                    for (i in 0..totalHours) {
                        val y = i * hourHeight.toPx()
                        drawLine(Color.LightGray, Offset(0f, y), Offset(fullWidth, y), 1f)
                    }
                }

                // Red now line
                if (date == LocalDate.now()) {
                    val nowMinutes = now.hour * 60 + now.minute
                    val nowOffset = (nowMinutes / 60f) * hourHeight.value

                    Row(
                        modifier = Modifier
                            .absoluteOffset(y = nowOffset.dp)
                            .fillMaxWidth()
                            .zIndex(2f)
                            .padding(start = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(now.format(DateTimeFormatter.ofPattern("HH:mm")), color = Color.Red, fontSize = 12.sp)
                        Canvas(modifier = Modifier.fillMaxWidth().height(1.dp)) {
                            drawLine(Color.Red, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth = 2f)
                        }
                    }
                }

                // Render events
                groups.forEach { group ->
                    val totalCols = group.size
                    val spacing = 2.dp
                    val availableWidth = screenWidth - 60.dp - spacing * (totalCols - 1)
                    val columnWidth = availableWidth / totalCols

                    group.sortedBy { it.startTime }.forEachIndexed { index, event ->
                        // Chuyển LocalDateTime -> LocalTime để lấy thời gian trong ngày
                        val startMin = event.startTime.toLocalTime().toSecondOfDay() / 60
                        val endMin = event.endTime.toLocalTime().toSecondOfDay() / 60

                        val top = (startMin / 60f) * hourHeight.value
                        val height = ((endMin - startMin) / 60f) * hourHeight.value
                        val offsetX = (columnWidth + spacing) * index

                        Box(
                            modifier = Modifier
                                .absoluteOffset(x = offsetX, y = top.dp)
                                .width(columnWidth)
                                .height(height.dp)
                                .padding(1.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(event.color.copy(alpha = 0.7f))
                                .border(1.dp, Color.White, RoundedCornerShape(4.dp))
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
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateOverlapsByGroup(events: List<DailyCalendarEvent>): List<List<DailyCalendarEvent>> {
    val sorted = events.sortedBy { it.startTime }
    val groups = mutableListOf<MutableList<DailyCalendarEvent>>()

    for (event in sorted) {
        val overlappingGroup = groups.find { group ->
            group.any { it.startTime < event.endTime && event.startTime < it.endTime }
        }
        if (overlappingGroup != null) {
            overlappingGroup.add(event)
        } else {
            groups.add(mutableListOf(event))
        }
    }
    return groups
}

@RequiresApi(Build.VERSION_CODES.O)
fun getStartOfWeek(today: LocalDate): LocalDate {
    val dayOfWeek = today.dayOfWeek.value
    return today.minusDays((dayOfWeek - 1).toLong())
}