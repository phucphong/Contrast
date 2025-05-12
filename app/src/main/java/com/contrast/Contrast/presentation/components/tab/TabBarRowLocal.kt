package com.contrast.Contrast.presentation.components.tab


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.extensions.label

import com.contrast.Contrast.presentation.theme.FFE0E0E0
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.category.Category


@Composable
fun TabBarRowLocal(
    tabs: List<Category>,
    selectedTab: Int,

    paddingBottom: Dp =2.dp,
    fillMaxWidth: Float=1f,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.White)
    ) {
        tabs.forEachIndexed { index, obj ->
            val isSelected = index == selectedTab


            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onTabSelected(index)
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = obj.label(),
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) TealGreen else Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .fillMaxWidth(fillMaxWidth) // Gạch dưới chiếm 80% chiều rộng
                                .background(TealGreen).padding(bottom =paddingBottom)
                        )
                    }
                }
            }

            // Gạch chia giữa các tab (trừ tab cuối)
            if (index != tabs.lastIndex) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(20.dp)
                        .align(Alignment.CenterVertically)
                        .background(FFE0E0E0)
                )
            }
        }
    }
}
