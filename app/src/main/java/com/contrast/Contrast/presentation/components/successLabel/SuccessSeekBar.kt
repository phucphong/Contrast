package com.contrast.Contrast.presentation.components.successLabel





import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.theme.FF7C7C7C
import com.contrast.Contrast.presentation.theme.LightGrayBackground
import com.contrast.Contrast.presentation.theme.TealGreen

@Composable
fun SucceccSeekBar(
    progress: Float, // 0f -> 1f
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
                    .background(FF7C7C7C)
            )

            // Orange progress
            Box(
                modifier = Modifier
                    .fillMaxWidth(clampedProgress)
                    .height(trackHeight)
                    .clip(RoundedCornerShape(thumbSize / 2))
                    .background(Color(0xFFFF9800) )
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
                                colors =  listOf(Color(0xFFFFEB3B), Color(0xFFFE8B14)),
                                start = Offset.Zero,
                                end = Offset(size.width, size.height)
                            )
                        )
                    }


                }
            }
        }

        Spacer(modifier = Modifier.width(6.dp))



    }
}
