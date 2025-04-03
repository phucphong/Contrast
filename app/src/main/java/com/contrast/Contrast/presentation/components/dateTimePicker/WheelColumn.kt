package com.contrast.Contrast.presentation.components.dateTimePicker
import android.util.Log
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.util.Calendar
// Common wheel column with snap (for hour/minute)
@Composable
fun WheelColumn(
    items: List<Int>,
    centerIndex: Int,
    repeatedItems: Int,
    selectedValue: Int,
    onValueChange: (Int) -> Unit
) {
    val itemHeight = 40.dp
    val visibleItems = 5
    val totalHeight = itemHeight * visibleItems

    Log.e("selectedValueDay",selectedValue.toString())

    // Tạo danh sách lặp lại với các mục, đảm bảo chọn đúng chỉ mục
    val repeatedItems = remember(items) { List(repeatedItems) { items }.flatten() }
    val centerIndex = repeatedItems.size / 2 + items.indexOf(selectedValue-centerIndex)
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = centerIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LaunchedEffect(listState.firstVisibleItemIndex) {
        val currentIndex = listState.firstVisibleItemIndex
        if (currentIndex < visibleItems || currentIndex > repeatedItems.size - visibleItems) {
            listState.scrollToItem(centerIndex)
        }

        val center = currentIndex + visibleItems / 2
        if (center in repeatedItems.indices) {
            onValueChange(repeatedItems[center % items.size])
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(60.dp).height(totalHeight)
        ) {
            itemsIndexed(repeatedItems) { index, item ->

                Log.e("selectedValueDay"," index $index")
                val isSelected = index == listState.firstVisibleItemIndex + visibleItems / 2
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.toString().padStart(2, '0'),
                        textAlign = TextAlign.Center,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = Color.Black
                    )
                }
            }
        }
    }
}


fun getWeekdayShort(date: Triple<Int, Int, Int>): String {
    val (day, month, year) = date
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)
        set(Calendar.DAY_OF_MONTH, day)
    }
    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "CN"
        Calendar.MONDAY -> "Th 2"
        Calendar.TUESDAY -> "Th 3"
        Calendar.WEDNESDAY -> "Th 4"
        Calendar.THURSDAY -> "Th 5"
        Calendar.FRIDAY -> "Th 6"
        Calendar.SATURDAY -> "Th 7"
        else -> ""
    }
}

