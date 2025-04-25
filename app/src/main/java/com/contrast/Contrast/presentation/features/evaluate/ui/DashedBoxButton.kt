package com.contrast.Contrast.presentation.features.evaluate.ui
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.CornerRadius

import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import com.contrast.Contrast.presentation.theme.FFD7D7D7

@Composable
fun DashedBoxButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(80.dp)
            .clickable { onClick() }
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val dashWidth = 10f
            val dashGap = 10f
            val cornerRadius = 10.dp.toPx()
            val strokeWidth = 1.dp.toPx()

            val paint = Paint().apply {
                color = FFD7D7D7
                style = PaintingStyle.Stroke
                this.strokeWidth = strokeWidth
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap))
            }

            val path = Path().apply {
                addRoundRect(
                    RoundRect(
                        rect = Rect(0f, 0f, size.width, size.height),
                        cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                    )
                )
            }

            drawPath(
                path = path,
                color = FFD7D7D7,
                style = Stroke(
                    width = strokeWidth,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap))
                )
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, modifier = Modifier.size(24.dp), tint = FFD7D7D7)
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, style = MaterialTheme.typography.labelSmall)
        }
    }
}

