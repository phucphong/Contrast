package com.contrast.Contrast.presentation.components.dateTimePicker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatDateTimeDDMMYYYY_HHMM
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.F6F6F6
import com.contrast.Contrast.presentation.theme.FF0967DF
import java.text.SimpleDateFormat
import java.util.*
@Composable
fun IOSStyleWheelDateTimePicker(
    modifier: Modifier = Modifier,
    initialDateTime: String? = null,  // Chuỗi định dạng "dd/MM/yyyy HH:mm"
    onDateTimeSelected: (String) -> Unit,
    minDateTime: String? = null,  // Tùy chọn min date time
    maxDateTime: String? = null   // Tùy chọn max date time
) {
    // Lưu trữ calendar để không bị thay đổi nhiều lần
    val systemCalendar = remember { Calendar.getInstance(TimeZone.getDefault()) }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()

    // Kiểm tra nếu có initialDateTime, nếu có parse nó, nếu không thì sử dụng thời gian hệ thống
    if (!initialDateTime.isNullOrEmpty()) {
        try {
            val parsedDate = dateFormat.parse(initialDateTime)
            parsedDate?.let { date -> systemCalendar.time = date }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else {
        systemCalendar.time = Calendar.getInstance().time
    }

    val initialDay = systemCalendar.get(Calendar.DAY_OF_MONTH)
    val initialMonth = systemCalendar.get(Calendar.MONTH) + 1
    val initialYear = systemCalendar.get(Calendar.YEAR)
    var initialHour = systemCalendar.get(Calendar.HOUR_OF_DAY)
    var initialMinute = systemCalendar.get(Calendar.MINUTE)

    val hours = (0..23).toList()
    val minutes = (0..59).toList()

    // Tạo danh sách ngày tháng năm
    val dates = remember {
        mutableListOf<Triple<Int, Int, Int>>().apply {
            for (y in 1900..2050) {
                for (m in 1..12) {
                    val maxDay = when (m) {
                        2 -> if (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0)) 29 else 28
                        4, 6, 9, 11 -> 30
                        else -> 31
                    }
                    for (d in 1..maxDay) {
                        add(Triple(d, m, y))
                    }
                }
            }
        }
    }

    // Chỉ số ngày tháng hiện tại
    var selectedHour by remember { mutableStateOf(initialHour) }
    var selectedMinute by remember { mutableStateOf(initialMinute) }

    var selectedDateIndex by remember {
        mutableStateOf(
            // Kiểm tra nếu ngày đã có trong danh sách và lấy chỉ mục nếu có, nếu không lấy 0
            dates.indexOfFirst { it.first == initialDay && it.second == initialMonth && it.third == initialYear }
                .takeIf { it != -1 } ?: 0 // Nếu không tìm thấy, đặt lại về chỉ mục 0
        )
    }

    // Logic giới hạn thời gian
    val minCalendar = minDateTime?.let { dateFormat.parse(it)?.let { Calendar.getInstance().apply { time = it } } }
    val maxCalendar = maxDateTime?.let { dateFormat.parse(it)?.let { Calendar.getInstance().apply { time = it } } }

    // Kiểm tra giới hạn ngày tháng
    fun isDateValid(day: Int, month: Int, year: Int): Boolean {
        val selectedDate = Calendar.getInstance().apply {
            set(year, month - 1, day) // Tháng trong Calendar bắt đầu từ 0
        }
        return (minCalendar == null || selectedDate.timeInMillis >= minCalendar.timeInMillis) &&
                (maxCalendar == null || selectedDate.timeInMillis <= maxCalendar.timeInMillis)
    }

    // Kiểm tra giờ
    fun isHourValid(hour: Int): Boolean {
        val selectedDate = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
        }
        return (minCalendar == null || selectedDate.timeInMillis >= minCalendar.timeInMillis) &&
                (maxCalendar == null || selectedDate.timeInMillis <= maxCalendar.timeInMillis)
    }

    // Kiểm tra phút
    fun isMinuteValid(minute: Int): Boolean {
        val selectedDate = Calendar.getInstance().apply {
            set(Calendar.MINUTE, minute)
        }
        return (minCalendar == null || selectedDate.timeInMillis >= minCalendar.timeInMillis) &&
                (maxCalendar == null || selectedDate.timeInMillis <= maxCalendar.timeInMillis)
    }

    LaunchedEffect(selectedDateIndex, selectedHour, selectedMinute) {
        // Log debug chỉ khi có sự thay đổi thực sự
        Log.d("DateTimePicker", "🛠 DEBUG: DateTimePicker Updated - Day: ${dates[selectedDateIndex].first}, Month: ${dates[selectedDateIndex].second}, Year: ${dates[selectedDateIndex].third}, Hour: $selectedHour, Minute: $selectedMinute")
    }

    Column(
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(Color.White)
                    .noRippleClickableComposable {
                        val (day, month, year) = dates[selectedDateIndex]
                        val result = formatDateTimeDDMMYYYY_HHMM(day, month, year, selectedHour, selectedMinute)
                        onDateTimeSelected(result)
                    }
            ) {
                CustomText(
                    text = stringResource(R.string.done),
                    color = FF0967DF,
                    textAlign = TextAlign.End
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(F6F6F6)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WheelColumn(
                    items = hours,
                    repeatedItems = 48,
                    centerIndex = 2,
                    selectedValue = selectedHour,
                    onValueChange = { if (isHourValid(it)) selectedHour = it }
                )

                WheelColumn(
                    items = minutes,
                    repeatedItems = 100,
                    centerIndex = 2,
                    selectedValue = selectedMinute,
                    onValueChange = { if (isMinuteValid(it)) selectedMinute = it }
                )

                WheelDateColumn(
                    items = dates,
                    selectedIndex = selectedDateIndex,
                    onSelected = {
                        val (day, month, year) = dates[it]
                        if (isDateValid(day, month, year)) selectedDateIndex = it
                    }
                )
            }

            SelectorLines(itemHeight = 40.dp, totalHeight = 240.dp)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
