// TimelineWeekViewCompose FIXED: scroll event theo timeline + hỗ trợ overlap event chia cột ngang như iOS + full width nếu 1 event
package com.contrast.Contrast.presentation.components.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.delay

// Dữ liệu sự kiện nhiều người
data class MultiUserEvent(
    val userId: String,
    val title: String,
    val color: Color,
    val startTime: LocalTime,
    val endTime: LocalTime
)

data class PositionedMultiUserEvent(
    val event: MultiUserEvent,
    val column: Int,
    val totalColumns: Int
)

interface TimelineWeekViewListener {
    fun onTapEvent(event: MultiUserEvent)
    fun onLongPressEvent(event: MultiUserEvent)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineWeekView(
    users: List<String>,
    date: LocalDate,
    events: List<MultiUserEvent>,
    modifier: Modifier = Modifier,
    listener: TimelineWeekViewListener? = null
) {
    val hourHeight = 80.dp
    val timeColumnWidth = 50.dp
    val userColumnWidth = 120.dp
    val totalHours = 24
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    val nowTime = remember { mutableStateOf(LocalTime.now()) }
    LaunchedEffect(Unit) {
        while (true) {
            nowTime.value = LocalTime.now()
            delay(60_000)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Header ngày + tên người dùng
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(horizontalScrollState)
                .background(Color(0xFFF6F6F6))
        ) {
            Spacer(modifier = Modifier.width(timeColumnWidth))
            users.forEach { userId ->
                Box(
                    modifier = Modifier
                        .width(userColumnWidth)
                        .height(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = userId, fontSize = 14.sp)
                }
            }
        }

        // Nội dung timeline scrollable
        Box(
            modifier = Modifier
                .fillMaxSize()
                .horizontalScroll(horizontalScrollState)
        ) {
            Row {
                // Cột giờ
                Column(
                    modifier = Modifier
                        .width(timeColumnWidth)
                        .verticalScroll(verticalScrollState)
                ) {
                    repeat(totalHours) { hour ->
                        Box(
                            modifier = Modifier
                                .height(hourHeight)
                                .padding(start = 4.dp),
                            contentAlignment = Alignment.TopStart
                        ) {
                            Text("%02d:00".format(hour), fontSize = 12.sp)
                        }
                    }
                }

                // Các cột người dùng
                users.forEach { userId ->
                    Column(
                        modifier = Modifier
                            .width(userColumnWidth)
                            .verticalScroll(verticalScrollState)
                    ) {
                        Box {
                            Column {
                                repeat(totalHours) {
                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(1.dp),
                                        color = Color.LightGray
                                    )
                                    Spacer(modifier = Modifier.height(hourHeight - 1.dp))
                                }
                            }

                            // Tính toán overlap sự kiện theo userId
                            val userEvents = events.filter { it.userId == userId }
                            val positionedEvents = calculateOverlaps(userEvents)

                            // Event block scroll theo timeline và chia cột nếu overlap
                            positionedEvents.forEach { positioned ->
                                val event = positioned.event
                                val column = positioned.column
                                val totalCols = positioned.totalColumns

                                val startMinutes = event.startTime.toSecondOfDay() / 60
                                val endMinutes = event.endTime.toSecondOfDay() / 60
                                val topOffset = (startMinutes / 60f) * hourHeight.value
                                val height = ((endMinutes - startMinutes) / 60f) * hourHeight.value

                                val colWidth = if (totalCols == 1) userColumnWidth else userColumnWidth / totalCols
                                val offsetX = if (totalCols == 1) 0.dp else colWidth * column

                                Box(
                                    modifier = Modifier
                                        .absoluteOffset(x = offsetX, y = topOffset.dp)
                                        .width(colWidth - 4.dp)
                                        .padding(2.dp)
                                        .height(height.dp)
                                        .clip(MaterialTheme.shapes.small)
                                        .background(event.color)
                                        .zIndex(1f)
                                        .pointerInput(event) {
                                            detectTapGestures(
                                                onTap = { listener?.onTapEvent(event) },
                                                onLongPress = { listener?.onLongPressEvent(event) }
                                            )
                                        },
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

                            // Now line
                            if (LocalDate.now() == date) {
                                val now = nowTime.value
                                val nowMinutes = now.hour * 60 + now.minute
                                val offsetY = (nowMinutes / 60f) * hourHeight.value

                                Canvas(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .absoluteOffset(y = offsetY.dp)
                                        .zIndex(2f)
                                ) {
                                    drawLine(
                                        color = Color.Red,
                                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                                        strokeWidth = 2f
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateOverlaps(events: List<MultiUserEvent>): List<PositionedMultiUserEvent> {
    val sorted = events.sortedBy { it.startTime }
    val groups = mutableListOf<MutableList<MultiUserEvent>>()

    for (event in sorted) {
        var placed = false
        for (group in groups) {
            if (group.any { overlap(it, event) }) {
                group.add(event)
                placed = true
                break
            }
        }
        if (!placed) groups.add(mutableListOf(event))
    }

    val positioned = mutableListOf<PositionedMultiUserEvent>()
    for (group in groups) {
        val total = group.size
        group.sortedBy { it.startTime }.forEachIndexed { index, ev ->
            positioned.add(PositionedMultiUserEvent(ev, index, total))
        }
    }
    return positioned
}

@RequiresApi(Build.VERSION_CODES.O)
fun overlap(a: MultiUserEvent, b: MultiUserEvent): Boolean {
    return a.startTime < b.endTime && b.startTime < a.endTime
}
