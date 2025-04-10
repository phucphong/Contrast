package com.contrast.Contrast.presentation.components.tab


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.theme.EEEEEF
import com.contrast.Contrast.presentation.theme.FF888888
import com.contrast.Contrast.presentation.theme.FFAFAFAF

import com.contrast.Contrast.presentation.theme.FFD7D7D7
import com.contrast.Contrast.presentation.theme.FFE0E0E0
@Composable
fun SegmentTab(
    tabs: List<String>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .height(35.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, EEEEEF, RoundedCornerShape(8.dp))
            .background(EEEEEF)
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = index == selectedTab

            // Divider TRÁI
            if (index != 0) {
                val isPreviousSelected = selectedTab == index - 1 || isSelected
                val dividerColor = if (isPreviousSelected) FFE0E0E0 else FFAFAFAF

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(dividerColor)
                )
            }

            // TAB
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(if (isSelected) Color.White else Color.Transparent)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onTabSelected(index) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = Color.Black
                )
            }

            // Divider PHẢI nếu là selected (trừ tab cuối)
            if (isSelected && index != tabs.lastIndex) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(FFE0E0E0)
                )
            }
        }
    }
}
