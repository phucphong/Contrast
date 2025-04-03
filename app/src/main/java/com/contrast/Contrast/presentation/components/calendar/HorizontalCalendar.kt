package com.contrast.Contrast.presentation.components.calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.theme.FF037BFF

import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import java.time.DayOfWeek

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*
@RequiresApi(Build.VERSION_CODES.O)

@Composable
fun HorizontalCalendar(
    modifier: Modifier = Modifier,
    eventDates: List<LocalDate> = emptyList(),
    onDateSelected: (LocalDate) -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = DayOfWeek.SUNDAY
    val daysOfWeek = daysOfWeek(firstDayOfWeek)

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Column(modifier = modifier) {
        MonthHeader(calendarState.firstVisibleMonth.yearMonth)
        DaysOfWeekTitle(daysOfWeek = daysOfWeek)

        HorizontalCalendar(
            state = calendarState,
            dayContent = { day ->
                val isSelected = day.date == selectedDate
                val hasEvent = eventDates.contains(day.date)
                Day(
                    day = day,
                    isSelected = isSelected,
                    hasEvent = hasEvent,
                    onClick = { clickedDay ->
                        selectedDate = clickedDay.date
                        onDateSelected(clickedDay.date)
                    }
                )
            }
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthHeader(yearMonth: YearMonth) {
    val monthText = yearMonth.month
        .getDisplayName(TextStyle.FULL, Locale.getDefault())
        .replaceFirstChar { it.uppercase() } + " ${yearMonth.year}"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = monthText,
            style = MaterialTheme.typography.titleMedium,
            color = FF037BFF // Màu xanh giống iOS
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(2.dp),
                color = FF037BFF,
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    hasEvent: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    val today = remember { LocalDate.now() }
    val isToday = day.date == today

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clickable { onClick(day) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isSelected -> Color(0xFF2196F3) // Màu xanh cho ngày được chọn
                            isToday -> Color(0xFFFFA500) // Màu cam cho ngày hiện tại
                            else -> Color.Transparent
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = if (isSelected || isToday) Color.White else Color.Black,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (hasEvent) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(Color.Blue)
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun DayOfWeek.daysOfWeek(): List<DayOfWeek> {
    val daysOfWeek = DayOfWeek.values()
    val firstIndex = this.ordinal
    return daysOfWeek.slice(firstIndex..6) + daysOfWeek.slice(0 until firstIndex)
}
