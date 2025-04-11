package com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomCircularProgressIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 30.dp,
    strokeWidth: Dp = 5.dp
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedSweep = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing)
        )
    )


    Box(
        modifier = Modifier.fillMaxSize().padding(top = 20.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(
            modifier = modifier.size(size),

            ) {
            val radius = (size.toPx() / 2) - (strokeWidth.toPx() / 2)
            val stroke = strokeWidth.toPx()
            val center = Offset(size.toPx() / 2, size.toPx() / 2) // ✅ Căn giữa chính xác

            // 1️⃣ Vẽ vòng tròn nền (Màu trắng)
            drawCircle(
                color = Color.White,
                radius = radius,
                center = center, // ✅ Căn giữa
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )

            // 2️⃣ Vẽ progress với gradient đỏ trên vòng tròn nền
            val brush = Brush.sweepGradient(
                colors = listOf(Color.Transparent, Color.Red, Color.Transparent),
                center = center
            )

            drawArc(
                brush = brush,
                startAngle = animatedSweep.value,
                sweepAngle = 100f, // ✅ Điều chỉnh độ dài progress
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius), // ✅ Căn giữa
                size = Size(radius * 2, radius * 2), // ✅ Căn chính xác kích thước
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }
    }

}
