package com.contrast.Contrast.presentation.components.tab

import com.itechpro.domain.model.Category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.extensions.splitTextToTwoLines
import com.contrast.Contrast.presentation.components.image.NetworkImage
@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun TabBarRowCircle(
    tabs: List<Category>,
    color: Color = Color.Black,
    textCorSelect: Color = Color.White,
    selectedTab: Int,
    type: String?,
    domain: String,
    onTabSelected: (Int) -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedTab) {
        val targetIndex = selectedTab.coerceAtMost(tabs.size - 1)
        listState.animateScrollToItem(index = maxOf(0, targetIndex - 1))
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = 8.dp)
    ) {
        items(tabs.size) { index ->
            val name = if (type == "name") tabs[index].name ?: "" else tabs[index].ten ?: ""
            val fullUrl = domain.trimEnd('/') + (tabs[index].filetxt ?: "")
            val isSelected = index == selectedTab

            Column(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable {
                        onTabSelected(index)
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Circle Avatar
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White, shape = CircleShape)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color.Black else Color.Transparent,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    NetworkImage(
                        imageUrl = fullUrl,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Text with background if selected
                Box(
                    modifier = if (isSelected) {
                        Modifier
                            .background(Color.Black, shape = RoundedCornerShape(20.dp))
                            .padding(horizontal = 4.dp, vertical = 4.dp).width(70.dp)
                    } else {
                        Modifier
                            .padding(horizontal = 4.dp, vertical = 4.dp)
                    }
                    , contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = splitTextToTwoLines(name),
                        fontSize = 11.sp,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontWeight =  FontWeight.Normal,
                        maxLines = 2,
                        minLines = 2,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
