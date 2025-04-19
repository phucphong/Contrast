package com.contrast.Contrast.presentation.components.progressBar
import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
@Composable
fun FlashSaleSeekBar(
    progress: Float, // 0f -> 1f
    remainingTime: String,
    modifier: Modifier = Modifier
) {
    val clampedProgress = progress.coerceIn(0f, 1f)
    val thumbSize = 12.dp
    val trackHeight = 3.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(thumbSize).padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Thanh seekbar + icon thumb
        Box(
            modifier = Modifier
                .weight(1f)
                .height(thumbSize),
            contentAlignment = Alignment.CenterStart
        ) {
            // Nền xám
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(trackHeight)
                    .clip(RoundedCornerShape(thumbSize / 2))
                    .background(Color(0xFFE0E0E0))
            )

            // Tiến trình cam
            Box(
                modifier = Modifier
                    .fillMaxWidth(clampedProgress)
                    .height(trackHeight)
                    .clip(RoundedCornerShape(thumbSize / 2))
                    .background(Color(0xFFFF9800))
            )

            // Icon đồng hồ chạy theo progress
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = (clampedProgress * 100).dp)
            ) {
                Canvas(
                    modifier = Modifier
                        .size(thumbSize)
                        .align(Alignment.CenterStart)
                ) {
                    drawCircle(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFFFEB3B), Color(0xFFFE8B14)),
                            start = Offset.Zero,
                            end = Offset(size.width, size.height)
                        )
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.clock_flash_sales),
                    contentDescription = "Clock",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(thumbSize)
                        .align(Alignment.CenterStart)
                )
            }
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Countdown text
        Text(
            text = remainingTime.replaceFirst("00:",""),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFFF9800)
        )
    }
}
