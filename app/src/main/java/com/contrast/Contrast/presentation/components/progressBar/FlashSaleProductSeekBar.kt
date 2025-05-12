package com.contrast.Contrast.presentation.components.progressBar



import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.contrast.Contrast.R

@Composable
fun FlashSaleProductSeekBar(
    progress: Float, // 0f -> 1f
    remainingTime: String,
    isFlashSale: Boolean = true,

    modifier: Modifier = Modifier
) {
    val clampedProgress = progress.coerceIn(0f, 1f)
    val thumbSize = 12.dp
    val trackHeight = 3.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(thumbSize)
    ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(thumbSize),
            contentAlignment = Alignment.CenterStart
        ) {
            // Background gray track
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(trackHeight)
                    .clip(RoundedCornerShape(thumbSize / 2))
                    .background(Color(0xFFE0E0E0))
            )

            // Orange progress
            Box(
                modifier = Modifier
                    .fillMaxWidth(clampedProgress)
                    .height(trackHeight)
                    .clip(RoundedCornerShape(thumbSize / 2))
                    .background(if(isFlashSale)Color(0xFFFF9800) else Color.White)
            )

            // Clock icon moving with progress
            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth()
            ) {
                val maxWidthPx = constraints.maxWidth.toFloat()
                val offsetPx = maxWidthPx * clampedProgress
                val offsetDp = with(LocalDensity.current) { offsetPx.toDp() }

                Box(modifier = Modifier.padding(start = offsetDp)) {
                    Canvas(
                        modifier = Modifier
                            .size(thumbSize)
                            .align(Alignment.CenterStart)
                    ) {
                        drawCircle(
                            brush = Brush.linearGradient(
                                colors =  if(isFlashSale) listOf(Color(0xFFFFEB3B), Color(0xFFFE8B14)) else listOf(Color.White, Color.White),
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
        }

        Spacer(modifier = Modifier.width(6.dp))


            androidx.compose.material.Text(
                text = remainingTime.replaceFirst("00:", ""),
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = if(isFlashSale) Color(0xFFFF9800)else Color.White
            )

    }
}
