package com.contrast.Contrast.presentation.components.tab
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itechpro.domain.model.category.Category

@Composable
fun TabBarRowPillStyle(
    tabs: List<Category>,
    selectedTab: Int,
    type: String?,
    onTabSelected: (Int) -> Unit
) {
    val listState = rememberLazyListState()

//    LaunchedEffect(selectedTab) {
//        val targetIndex = if (selectedTab >= tabs.size - 1) tabs.size - 1 else selectedTab
//        listState.animateScrollToItem(index = maxOf(0, targetIndex - 1))
//    }

    // Auto scroll
    LaunchedEffect(selectedTab) {
        val itemInfo = listState.layoutInfo.visibleItemsInfo.find { it.index == selectedTab }

        val viewportCenter = listState.layoutInfo.viewportEndOffset / 2
        val itemOffset = itemInfo?.offset ?: 0
        val itemSize = itemInfo?.size ?: 0
        val scrollOffset = itemOffset + itemSize / 2 - viewportCenter

        listState.animateScrollBy(scrollOffset.toFloat())
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tabs.size) { index ->
            val isSelected = index == selectedTab
            val name = if (type == "name") tabs[index].name ?: "" else tabs[index].ten ?: ""

            Box(
                modifier = Modifier
                    .clickable {
                        onTabSelected(index)
                    }
                    .background(
                        color =Color.White ,
                        shape = RoundedCornerShape(50)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color.Black else Color.LightGray,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 13.sp,
                    color = if (isSelected) Color.Black else Color.Gray,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}
