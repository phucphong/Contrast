package com.contrast.Contrast.presentation.components.datePicker
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
import com.contrast.Contrast.extensions.formatDateTimeDDMMYYYY
import com.contrast.Contrast.extensions.formatDateTimeDDMMYYYY_HHMM
import com.contrast.Contrast.presentation.components.dateTimePicker.SelectorLines
import com.contrast.Contrast.presentation.components.dateTimePicker.WheelColumn

import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.F6F6F6
import com.contrast.Contrast.presentation.theme.FF0967DF
import java.text.SimpleDateFormat
import java.util.*
@Composable
fun IOSStyleWheelDatePicker(
    modifier: Modifier = Modifier,
    initialDate: String? = null,  // Chuỗi định dạng "dd/MM/yyyy"
    onDateSelected: (String) -> Unit,
    minDate: String? = null,  // Tùy chọn min date
    maxDate: String? = null   // Tùy chọn max date
) {
    // Lưu trữ calendar để không bị thay đổi nhiều lần
    val systemCalendar = remember { Calendar.getInstance(TimeZone.getDefault()) }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getDefault()

    // Kiểm tra nếu có initialDate, nếu có parse nó, nếu không thì sử dụng thời gian hệ thống
    if (!initialDate.isNullOrEmpty()) {
        try {
            val parsedDate = dateFormat.parse(initialDate)
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

    // Tạo danh sách ngày, tháng, năm
    val days = (1..31).toList()
    val months = (1..12).toList()
    val years = (1900..2050).toList()

    // Chỉ số ngày, tháng, năm hiện tại
    var selectedDay by remember { mutableStateOf(initialDay) }
    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedYear by remember { mutableStateOf(initialYear) }

    // Logic giới hạn ngày tháng năm
    val minCalendar = minDate?.let { dateFormat.parse(it)?.let { Calendar.getInstance().apply { time = it } } }
    val maxCalendar = maxDate?.let { dateFormat.parse(it)?.let { Calendar.getInstance().apply { time = it } } }

    // Kiểm tra ngày
    fun isDateValid(day: Int, month: Int, year: Int): Boolean {
        val selectedDate = Calendar.getInstance().apply {
            set(year, month - 1, day) // Tháng trong Calendar bắt đầu từ 0
        }
        return (minCalendar == null || selectedDate.timeInMillis >= minCalendar.timeInMillis) &&
                (maxCalendar == null || selectedDate.timeInMillis <= maxCalendar.timeInMillis)
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
                        val result = formatDateTimeDDMMYYYY(selectedDay, selectedMonth, selectedYear)
                        onDateSelected(result)
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
                // Cột chọn ngày
                WheelColumn(
                    items = days,
                    repeatedItems = 48,
                    centerIndex = 2,
                    selectedValue = selectedDay,
                    onValueChange = { if (isDateValid(it, selectedMonth, selectedYear)) selectedDay = it }
                )

                // Cột chọn tháng
                WheelColumn(
                    items = months,
                    repeatedItems = 48,
                    centerIndex = 2,
                    selectedValue = selectedMonth,
                    onValueChange = { if (isDateValid(selectedDay, it, selectedYear)) selectedMonth = it }
                )

                // Cột chọn năm
                WheelColumn(
                    items = years,
                    repeatedItems = 48,
                    centerIndex = 2,
                    selectedValue = selectedYear,
                    onValueChange = { if (isDateValid(selectedDay, selectedMonth, it)) selectedYear = it }
                )
            }

            SelectorLines(itemHeight = 40.dp, totalHeight = 240.dp)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

