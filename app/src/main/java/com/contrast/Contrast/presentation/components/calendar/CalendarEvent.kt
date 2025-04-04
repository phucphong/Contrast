package com.contrast.Contrast.presentation.components.calendar


import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime
import java.time.LocalTime

data class CalendarEvent(
    val id: String,
    val title: String,
    val location: String,

    val color: Color,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val isAllDay: Boolean = true // 👈 THÊM VÀO
)
