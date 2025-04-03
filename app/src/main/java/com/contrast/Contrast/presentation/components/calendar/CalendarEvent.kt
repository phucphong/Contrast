package com.contrast.Contrast.presentation.components.calendar


import androidx.compose.ui.graphics.Color
import java.time.LocalTime

data class CalendarEvent(
    val id: String,
    val title: String,
    val location: String,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val color: Color
)
