package com.contrast.Contrast.presentation.components.calendarMore

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.contrast.Contrast.presentation.components.calendar.DailyCalendarEvent

interface TimelineDailyListener {
    fun onAddEvent(time: LocalTime)
    fun onLongPress(time: LocalTime)
    fun onClickEvent(event: DailyCalendarEvent)
}
// Bạn đang yêu cầu chia timeline ra 2 vùng:
// 1. Event toàn ngày hoặc diễn ra cả ngày => hiển thị riêng trong 1 cell ở trên
// 2. Event theo giờ => TimelineSingleDayContent hiển thị bên dưới

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineDailyViewMore(
    events: Map<LocalDate, List<DailyCalendarEvent>>,
    modifier: Modifier = Modifier,
    listener: TimelineDailyListener? = null
) {
    val hourHeight = 80.dp
    val currentWeekStart = remember { getStartOfWeek(LocalDate.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val daysOfWeek = remember(currentWeekStart) { (0..6).map { currentWeekStart.plusDays(it.toLong()) } }
    val nowTime = remember { mutableStateOf(LocalTime.now()) }

    var showDialog by remember { mutableStateOf(false) }
    var dialogEvents by remember { mutableStateOf<List<DailyCalendarEvent>>(emptyList()) }
    var dialogDate by remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            nowTime.value = LocalTime.now()
            delay(60_000)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd")

// Row hiển thị tên thứ
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            daysOfWeek.forEach { date ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date.dayOfWeek.name.take(2),
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
            }
        }

// LazyRow hiển thị ngày và cho phép scroll ngang
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(daysOfWeek) { date ->
                val isSelected = date == selectedDate

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color(0xFF1976D2) else Color.Transparent)
                        .clickable { selectedDate = date },
                    contentAlignment = Alignment.Center
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



        val selectedEvents = events[selectedDate].orEmpty()
        val allDayEvents = events[selectedDate].orEmpty().filter {
            it.startTime.toLocalTime() == LocalTime.MIDNIGHT &&
                    it.endTime.toLocalTime() == LocalTime.of(23, 59)
        }


        val normalEvents = selectedEvents - allDayEvents.toSet()


        if (allDayEvents.isNotEmpty()) {
            AllDayEventRow(
                allDayEvents = allDayEvents,
                onMoreClick = { hiddenEvents ->
                    dialogEvents = hiddenEvents
                    dialogDate = selectedDate
                    showDialog = true
                }
            )
        }


        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        TimelineSingleDayContent(
            date = selectedDate,
            events = normalEvents,
            now = nowTime.value,
            hourHeight = hourHeight,
            screenWidth = screenWidth,
            listener = listener,
            onShowMore = { date, group ->
                dialogDate = date
                dialogEvents = group
                showDialog = true
            }
        )
    }

    if (showDialog) {
        MoreEventsDialog(
            date = dialogDate,
            events = dialogEvents,
            onDismiss = { showDialog = false }
        )
    }
}
@Composable
fun AllDayEventRow(allDayEvents: List<DailyCalendarEvent>, onMoreClick: (List<DailyCalendarEvent>) -> Unit) {
    val visibleEvents = allDayEvents.take(4)
    val hiddenEvents = allDayEvents.drop(4)
    val totalCols = visibleEvents.size + if (hiddenEvents.isNotEmpty()) 1 else 0
    val spacing = 4.dp
    val columnWidth = (LocalConfiguration.current.screenWidthDp.dp - 60.dp - spacing * (totalCols - 1)) / totalCols

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD))
           ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Cả ngày", fontWeight = FontWeight.Medium, fontSize = 12.sp, modifier = Modifier.width(60.dp), textAlign = TextAlign.Center)

        Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
            visibleEvents.forEach { event ->
                Box(
                    modifier = Modifier
                        .width(columnWidth)
                        .height(80.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(event.color.copy(alpha = 0.7f))
                        .border(1.dp, Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = event.title,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding( 4.dp)
                    )
                }
            }

            if (hiddenEvents.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .width(columnWidth)
                        .height(80.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.LightGray)
                        .border(1.dp, Color.White)
                        .clickable { onMoreClick(hiddenEvents) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "+${hiddenEvents.size}", fontSize = 12.sp, color = Color.Black)
                }
            }
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
    screenWidth: Dp,
    listener: TimelineDailyListener?,
    onShowMore: (LocalDate, List<DailyCalendarEvent>) -> Unit
) {
    val scrollState = rememberScrollState()
    val totalHours = 24
    val hasScrolledToNow = remember { mutableStateOf(false) }

    LaunchedEffect(date) {
        if (!hasScrolledToNow.value) {
            val offset = ((now.hour * 60 + now.minute) / 60f) * hourHeight.value
            scrollState.scrollTo(offset.toInt())
            hasScrolledToNow.value = true
        }
    }

    val grouped = remember(events) { groupOverlappingEvents(events) }

    Row(Modifier.fillMaxSize()) {
        Column(Modifier.width(60.dp).verticalScroll(scrollState)) {
            repeat(totalHours) { hour ->
                Box(Modifier.height(hourHeight).padding(start = 4.dp)) {
                    Text("%02d:00".format(hour), fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        Box(
            Modifier.fillMaxSize()
                .verticalScroll(scrollState)
                .pointerInput(Unit) {
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
                Canvas(modifier = Modifier.fillMaxSize().zIndex(0f)) {
                    val fullWidth = size.width
                    for (i in 0..totalHours) {
                        val y = i * hourHeight.toPx()
                        drawLine(Color.LightGray, Offset(0f, y), Offset(fullWidth, y), 1f)
                    }
                }

                if (date == LocalDate.now()) {
                    val nowOffset = ((now.hour * 60 + now.minute) / 60f) * hourHeight.value
                    Row(
                        Modifier.absoluteOffset(y = nowOffset.dp)
                            .fillMaxWidth().zIndex(2f).padding(start = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(now.format(DateTimeFormatter.ofPattern("HH:mm")), color = Color.Red, fontSize = 12.sp)
                        Canvas(Modifier.fillMaxWidth().height(1.dp)) {
                            drawLine(Color.Red, Offset(0f, 0f), Offset(size.width, 0f), 2f)
                        }
                    }
                }

                grouped.forEachIndexed { groupIndex, group ->
                    val startMin = group.minOf { it.startTime.toLocalTime().toSecondOfDay() / 60 }
                    val endMin = group.maxOf { it.endTime.toLocalTime().toSecondOfDay() / 60 }

                    val top = (startMin / 60f) * hourHeight.value
                    val height = ((endMin - startMin) / 60f) * hourHeight.value

                    val visibleEvents = group.take(4)
                    val hiddenEvents = group.drop(4)
                    val totalCols = visibleEvents.size + if (hiddenEvents.isNotEmpty()) 1 else 0
                    val spacing = 2.dp
                    val availableWidth = screenWidth - 60.dp - spacing * (totalCols - 1)
                    val columnWidth = availableWidth / totalCols

                    visibleEvents.forEachIndexed { i, event ->
                        Box(
                            Modifier
                                .absoluteOffset(x = (columnWidth + spacing) * i, y = top.dp)
                                .width(columnWidth)
                                .height(height.dp)
                                .padding(1.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(event.color.copy(alpha = 0.7f))
                                .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                                .clickable { listener?.onClickEvent(event) }
                                .zIndex(groupIndex.toFloat()),
                            contentAlignment = Alignment.TopStart
                        ) {
                            Text(event.title, Modifier.padding(4.dp), fontSize = 12.sp, color = Color.Black)
                        }
                    }

                    if (hiddenEvents.isNotEmpty()) {
                        Box(
                            Modifier
                                .absoluteOffset(x = (columnWidth + spacing) * visibleEvents.size, y = top.dp)
                                .width(columnWidth)
                                .height(height.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.LightGray)
                                .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                                .clickable { onShowMore(date, group) }
                                .zIndex(groupIndex + 1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+${hiddenEvents.size}", fontSize = 12.sp, color = Color.Black)
                        }
                    }
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun groupOverlappingEvents(events: List<DailyCalendarEvent>): List<List<DailyCalendarEvent>> {
    val sorted = events.sortedBy { it.startTime }
    val groups = mutableListOf<MutableList<DailyCalendarEvent>>()
    for (event in sorted) {
        val group = groups.find { g -> g.any { e -> e.startTime < event.endTime && event.startTime < e.endTime } }
        if (group != null) group.add(event) else groups.add(mutableListOf(event))
    }
    return groups
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MoreEventsDialog(
    date: LocalDate,
    events: List<DailyCalendarEvent>,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                // Tiêu đề ngày
                Text(
                    text = "${date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }} - ${date.dayOfMonth}/${date.monthValue}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Danh sách sự kiện với LazyColumn
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp) // giới hạn chiều cao dialog
                ) {
                    itemsIndexed(events.sortedBy { it.startTime }) { index, event ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp).clickable{
                                    onDismiss
                                }
                        ) {
                            Text(
                                text = event.title,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                modifier =  Modifier.padding(vertical = 5.dp)
                            )
                            Text(
                                text = "${event.startTime} - ${event.endTime}",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        if (index < events.lastIndex) {
                            Divider(
                                color = Color(0xFFE0E0E0),
                                thickness = 1.dp
                            )
                        }
                    }
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
fun getStartOfWeek(today: LocalDate): LocalDate {
    val dayOfWeek = today.dayOfWeek.value
    return today.minusDays((dayOfWeek - 1).toLong())
}
