package com.contrast.Contrast.presentation.components.switch_custom



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DiscountTypeSwitch(
    isPercent: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderWidth = 1.dp
    val activeBg = Color.White
    val inactiveBg = Color(0xFFF0F0F0)
    val borderColor = Color.LightGray

    Row(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(inactiveBg)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                .background(if (isPercent) activeBg else inactiveBg)
                .border(
                    width = if (isPercent) borderWidth else 0.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                )
                .clickable { if (!isPercent) onToggle() }
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "%",
                fontWeight = if (isPercent) FontWeight.Bold else FontWeight.Normal,
                fontSize = 14.sp
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                .background(if (!isPercent) activeBg else inactiveBg)
                .border(
                    width = if (!isPercent) borderWidth else 0.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                )
                .clickable { if (isPercent) onToggle() }
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "VND",
                fontWeight = if (!isPercent) FontWeight.Bold else FontWeight.Normal,
                fontSize = 14.sp
            )
        }
    }
}
