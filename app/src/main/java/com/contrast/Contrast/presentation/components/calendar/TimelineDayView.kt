package com.contrast.Contrast.presentation.components.calendar

import android.os.Build
import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimelineDayView(
    events: List<CalendarEvent>,
    modifier: Modifier = Modifier,
    onEventClick: (CalendarEvent) -> Unit
) {
    val hourHeight = 80.dp
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Auto scroll đến giờ hiện tại khi mở
    LaunchedEffect(Unit) {
        delay(300)
        coroutineScope.launch {
            listState.animateScrollToItem(LocalTime.now().hour)
        }
    }

    val now = remember { mutableStateOf(LocalTime.now()) }

    // Cập nhật giờ hiện tại mỗi phút (nếu bạn muốn realtime)
    LaunchedEffect(Unit) {
        while (true) {
            now.value = LocalTime.now()
            delay(60_000)
        }
    }

    val nowMinutes = now.value.hour * 60 + now.value.minute
    val nowOffset = (nowMinutes / 60f) * hourHeight.value

    Box(modifier = modifier.fillMaxSize()) {

        // 1. Cột giờ
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(24) { hour ->
                Row(
                    modifier = Modifier
                        .height(hourHeight)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = String.format("%02d:00", hour),
                        modifier = Modifier.width(60.dp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                    Divider(
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(top = 20.dp),
                        color = Color.LightGray,
                        thickness = 0.5.dp
                    )
                }
            }
        }

        // 2. Now Line (dòng đỏ chỉ giờ hiện tại)
        // 2. Now Line hiển thị đúng vị trí phút hiện tại
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .absoluteOffset(y = nowOffset.dp)
                .zIndex(1f) // ✅ đảm bảo vẽ trên LazyColumn
                .padding(horizontal = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = now.value.format(DateTimeFormatter.ofPattern("HH:mm")),
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
                Spacer(modifier = Modifier.width(4.dp))
                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .weight(1f),
                    color = Color.Red,
                    thickness = 1.dp
                )
            }
        }

// 3. Event blocks
        events.forEach { event ->
            val startMinutes = event.startTime.hour * 60 + event.startTime.minute
            val endMinutesRaw = event.endTime.hour * 60 + event.endTime.minute
            val endMinutes = if (endMinutesRaw < startMinutes) endMinutesRaw + 1440 else endMinutesRaw

            val topOffset = (startMinutes / 60f) * hourHeight.value
            val eventHeight = ((endMinutes - startMinutes) / 60f) * hourHeight.value

            Log.d("event.title", "${event.title} topOffset=$topOffset height=$eventHeight")

            Box(
                modifier = Modifier
                    .offset(y = topOffset.dp + 4.dp)
                    .padding(start = 68.dp, end = 8.dp)
                    .fillMaxWidth()
                    .height(eventHeight.dp.coerceAtLeast(36.dp)) // ✅ đảm bảo hiển thị
                    .clip(RoundedCornerShape(6.dp))
                    .background(event.color)
                    .zIndex(0.5f), // ✅ không bị che
                contentAlignment = Alignment.CenterStart
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onEventClick(event) }
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                    Text(
                        text = "${event.startTime} - ${event.endTime}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

    }
}
