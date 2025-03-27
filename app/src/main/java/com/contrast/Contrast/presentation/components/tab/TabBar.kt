package com.contrast.Contrast.presentation.components.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R




@Composable
fun TabBar(tabs: List<String>, selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Row(modifier = Modifier.wrapContentWidth()) {
        tabs.forEachIndexed { index, tab ->
            Column(
                modifier = Modifier
                    .weight(1f) // chia đều mỗi tab
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null // ✅ Không hiệu ứng ripple
                    ) {
                        onTabSelected(index)
                    }
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = tab,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),

                    fontWeight = if (index == selectedTab) FontWeight(500) else  FontWeight(400),
                    color = if (index == selectedTab) Color.Black else Color.Gray
                )
                Spacer(modifier = Modifier.height(5.dp))
                if (index == selectedTab) {
                    Box(
                        modifier = Modifier
                            .height(2.dp)
                            .fillMaxWidth()
                            .background(Color.Red)

                    )
                }
            }
        }
    }
}
