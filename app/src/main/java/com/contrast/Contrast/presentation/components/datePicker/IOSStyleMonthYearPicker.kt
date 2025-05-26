package com.contrast.Contrast.presentation.components.datePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.dateTimePicker.SelectorLines
import com.contrast.Contrast.presentation.components.dateTimePicker.WheelColumn
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.F6F6F6
import com.contrast.Contrast.presentation.theme.FF0967DF

@Composable
fun IOSStyleMonthYearPicker(
    initialMonth: Int = 1,
    initialYear: Int = 2024,
    yearRange: IntRange = 2000..2100,
    onDateSelected: (month: Int, year: Int) -> Unit
) {
    val months = (1..12).toList()
    val years = yearRange.toList()

    var selectedMonth by remember { mutableStateOf(initialMonth) }
    var selectedYear by remember { mutableStateOf(initialYear) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header chứa nút Xong
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(Color.White)
                    .noRippleClickableComposable {
                        onDateSelected(selectedMonth, selectedYear)
                    }
            ) {
                CustomText(
                    text = stringResource(R.string.done),
                    color = FF0967DF,
                    textAlign = TextAlign.End
                )
            }
        }

        // Phần Wheel Picker
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
                // Wheel tháng
                WheelColumn(
                    items = months,
                    repeatedItems = 48,
                    centerIndex = 2,
                    selectedValue = selectedMonth,
                    onValueChange = { selectedMonth = it }
                )

                // Wheel năm
                WheelColumn(
                    items = years,
                    repeatedItems = 48,
                    centerIndex = 2,
                    selectedValue = selectedYear,
                    onValueChange = { selectedYear = it }
                )
            }

            // Vạch căn giữa
            SelectorLines(itemHeight = 40.dp, totalHeight = 240.dp)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
