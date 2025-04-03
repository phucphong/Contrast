package com.contrast.Contrast.presentation.components.dateTimePicker

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WheelDateColumn(
    items: List<Triple<Int, Int, Int>>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    val itemHeight = 40.dp
    val visibleItems = 5
    val totalHeight = itemHeight * visibleItems

    // Tạo chỉ số bắt đầu và trạng thái của danh sách
    val centerIndex = selectedIndex-2
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = centerIndex)

    // Hành vi cuộn
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LaunchedEffect(listState.firstVisibleItemIndex) {
        val currentIndex = listState.firstVisibleItemIndex
        val centerItemIndex = currentIndex + visibleItems / 2

        // Kiểm tra và tự động cuộn nếu vượt quá ranh giới
        if (currentIndex < visibleItems || currentIndex > items.size - visibleItems) {
            listState.scrollToItem(centerIndex)
        }

        // Lấy item ở giữa và gửi index logic
        if (centerItemIndex in items.indices) {
            onSelected(centerItemIndex % items.size)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(120.dp).height(totalHeight)
        ) {
            items(items) { item ->
                val isSelected = listState.firstVisibleItemIndex + visibleItems / 2 == items.indexOf(item)
                val label = "${getWeekdayShort(item)} ${"%02d".format(item.first)} thg ${"%02d".format(item.second)}"

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = label,
                        textAlign = TextAlign.Start,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = Color.Black
                    )
                }
            }
        }
    }
}
