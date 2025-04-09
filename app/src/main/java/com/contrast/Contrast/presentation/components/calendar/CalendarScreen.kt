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
//            val sampleEvents = mapOf(
//                today to listOf( // <-- dùng hôm nay
//                    DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 1",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(9, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 2",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 3",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0 ,0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 4",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 5",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 6",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 7",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 8",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 9",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 10",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 11",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 12",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(9, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 13",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(12, 0),
//                        endTime = LocalTime.of(22, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 14",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(14, 0),
//                        endTime = LocalTime.of(14, 59)
//                    ),DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 11",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(9, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 21",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 31",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0 ,0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 41",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 51",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 61",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 71",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 81",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 91",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 101",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 111",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(0, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 121",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(9, 0),
//                        endTime = LocalTime.of(23, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 113",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(12, 0),
//                        endTime = LocalTime.of(22, 59)
//                    ), DailyCalendarEvent(
//                        id = "1",
//                        title = "CV 114",
//                        color = Color(0xFF81C784),
//                        startTime = LocalTime.of(14, 0),
//                        endTime = LocalTime.of(14, 59)
//                    )
//
//                )
//            )

            val sampleEvents = mapOf(
                today to listOf(
                    DailyCalendarEvent("1", "Họp dự án EZMAX", Color(0xFFEF5350), LocalDateTime.of(today, LocalTime.of(9, 0)), LocalDateTime.of(today, LocalTime.of(10, 30))),
                    DailyCalendarEvent("2", "Gọi khách hàng tiềm năng", Color(0xFF42A5F5), LocalDateTime.of(today, LocalTime.of(10, 0)), LocalDateTime.of(today, LocalTime.of(11, 0))),
                    DailyCalendarEvent("3", "Ăn trưa với team", Color(0xFF66BB6A), LocalDateTime.of(today, LocalTime.of(12, 0)), LocalDateTime.of(today, LocalTime.of(13, 0))),
                    DailyCalendarEvent("4", "Code tính năng chia sẻ link", Color(0xFFFFCA28), LocalDateTime.of(today, LocalTime.of(13, 0)), LocalDateTime.of(today, LocalTime.of(15, 30))),
                    DailyCalendarEvent("5", "Review pull request", Color(0xFFAB47BC), LocalDateTime.of(today, LocalTime.of(15, 0)), LocalDateTime.of(today, LocalTime.of(16, 0))),
                    DailyCalendarEvent("6", "Đi tập gym", Color(0xFF26C6DA), LocalDateTime.of(today, LocalTime.of(17, 30)), LocalDateTime.of(today, LocalTime.of(18, 30))),
                    DailyCalendarEvent("7", "Dọn dẹp hộp thư đến", Color(0xFF8D6E63), LocalDateTime.of(today, LocalTime.of(8, 0)), LocalDateTime.of(today, LocalTime.of(8, 45))),
                    DailyCalendarEvent("8", "Cuộc gọi với đối tác", Color(0xFF5C6BC0), LocalDateTime.of(today, LocalTime.of(10, 15)), LocalDateTime.of(today, LocalTime.of(11, 0))),
                    DailyCalendarEvent("9", "Viết tài liệu kỹ thuật", Color(0xFFFF7043), LocalDateTime.of(today, LocalTime.of(14, 0)), LocalDateTime.of(today, LocalTime.of(16, 30))),
                    DailyCalendarEvent("10", "Chạy bộ buổi tối", Color(0xFF26A69A), LocalDateTime.of(today, LocalTime.of(19, 0)), LocalDateTime.of(today, LocalTime.of(20, 0))),
                    DailyCalendarEvent("11", "Event full ngày", Color(0xFF9CCC65), LocalDateTime.of(today, LocalTime.of(0, 0)), LocalDateTime.of(today, LocalTime.of(23, 59))),
                    DailyCalendarEvent("12", "Sự kiện song song A", Color(0xFFBA68C8), LocalDateTime.of(today, LocalTime.of(13, 30)), LocalDateTime.of(today, LocalTime.of(15, 0))),
                    DailyCalendarEvent("13", "Sự kiện song song B", Color(0xFFFFA726), LocalDateTime.of(today, LocalTime.of(13, 45)), LocalDateTime.of(today, LocalTime.of(15, 15))),
                    DailyCalendarEvent("14", "Họp nhanh 15p", Color(0xFF78909C), LocalDateTime.of(today, LocalTime.of(16, 45)), LocalDateTime.of(today, LocalTime.of(17, 0))),
                    DailyCalendarEvent("15", "Phỏng vấn ứng viên", Color(0xFFD4E157), LocalDateTime.of(today, LocalTime.of(10, 30)), LocalDateTime.of(today, LocalTime.of(11, 30))),
                    DailyCalendarEvent("16", "Lên kế hoạch tuần tới", Color(0xFF7986CB), LocalDateTime.of(today, LocalTime.of(9, 30)), LocalDateTime.of(today, LocalTime.of(10, 0))),
                    DailyCalendarEvent("17", "Kiểm thử giao diện mobile", Color(0xFF4DB6AC), LocalDateTime.of(today, LocalTime.of(11, 0)), LocalDateTime.of(today, LocalTime.of(12, 0))),
                    DailyCalendarEvent("18", "Check email khách hàng", Color(0xFFFF8A65), LocalDateTime.of(today, LocalTime.of(8, 30)), LocalDateTime.of(today, LocalTime.of(9, 0))),
                    DailyCalendarEvent("19", "Trả lời bình luận app", Color(0xFF9575CD), LocalDateTime.of(today, LocalTime.of(16, 0)), LocalDateTime.of(today, LocalTime.of(16, 45))),
                    DailyCalendarEvent("20", "Gặp mặt đối tác nước ngoài", Color(0xFFA1887F), LocalDateTime.of(today, LocalTime.of(10, 0)), LocalDateTime.of(today, LocalTime.of(12, 0))),
                    DailyCalendarEvent("21", "Chỉnh sửa báo cáo tháng", Color(0xFF90A4AE), LocalDateTime.of(today, LocalTime.of(14, 30)), LocalDateTime.of(today, LocalTime.of(15, 30))),
                    DailyCalendarEvent("22", "Brainstorm sản phẩm mới", Color(0xFF81C784), LocalDateTime.of(today, LocalTime.of(13, 0)), LocalDateTime.of(today, LocalTime.of(14, 30))),
                    DailyCalendarEvent("23", "Họp team design", Color(0xFF4FC3F7), LocalDateTime.of(today, LocalTime.of(15, 30)), LocalDateTime.of(today, LocalTime.of(16, 30))),
                    DailyCalendarEvent("24", "Backup dữ liệu server", Color(0xFFF06292), LocalDateTime.of(today, LocalTime.of(18, 0)), LocalDateTime.of(today, LocalTime.of(18, 45))),
                    DailyCalendarEvent("25", "Nghiên cứu Flutter Web", Color(0xFF7986CB), LocalDateTime.of(today, LocalTime.of(8, 0)), LocalDateTime.of(today, LocalTime.of(9, 0))),
                    DailyCalendarEvent("26", "Test API mới triển khai", Color(0xFF64B5F6), LocalDateTime.of(today, LocalTime.of(9, 0)), LocalDateTime.of(today, LocalTime.of(10, 0))),
                    DailyCalendarEvent("27", "Giao lưu với dev team khác", Color(0xFFDCE775), LocalDateTime.of(today, LocalTime.of(14, 0)), LocalDateTime.of(today, LocalTime.of(15, 0))),
                    DailyCalendarEvent("28", "Hướng dẫn nhân sự mới", Color(0xFF4DD0E1), LocalDateTime.of(today, LocalTime.of(11, 30)), LocalDateTime.of(today, LocalTime.of(12, 30))),
                    DailyCalendarEvent("29", "Nâng cấp hệ thống backend", Color(0xFFBA68C8), LocalDateTime.of(today, LocalTime.of(16, 0)), LocalDateTime.of(today, LocalTime.of(17, 30))),
                    DailyCalendarEvent("30", "Tối ưu database", Color(0xFFFFB74D), LocalDateTime.of(today, LocalTime.of(10, 0)), LocalDateTime.of(today, LocalTime.of(11, 15))),
                    DailyCalendarEvent("31", "Xem lại roadmap quý 2", Color(0xFFAED581), LocalDateTime.of(today, LocalTime.of(15, 45)), LocalDateTime.of(today, LocalTime.of(17, 0))),
                    DailyCalendarEvent("32", "Cập nhật Jira task", Color(0xFF90CAF9), LocalDateTime.of(today, LocalTime.of(9, 15)), LocalDateTime.of(today, LocalTime.of(10, 0))),
                    DailyCalendarEvent("33", "Báo cáo kết quả tuần", Color(0xFFFF8A65), LocalDateTime.of(today, LocalTime.of(17, 0)), LocalDateTime.of(today, LocalTime.of(17, 30))),
                    DailyCalendarEvent("34", "Làm form khảo sát khách hàng", Color(0xFFCE93D8), LocalDateTime.of(today, LocalTime.of(13, 0)), LocalDateTime.of(today, LocalTime.of(14, 0))),
                    DailyCalendarEvent("35", "Cập nhật release note", Color(0xFFFFD54F), LocalDateTime.of(today, LocalTime.of(16, 0)), LocalDateTime.of(today, LocalTime.of(16, 45))),
                    DailyCalendarEvent("36", "Gặp mặt ban giám đốc", Color(0xFF26A69A), LocalDateTime.of(today, LocalTime.of(8, 0)), LocalDateTime.of(today, LocalTime.of(9, 0)))
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
