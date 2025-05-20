package com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered



import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun CustomCircularProgressIndicatorDialog(
    onDismissRequest: () -> Unit = {},
    show: Boolean = true,
) {
    if (show) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                CustomCircularProgressIndicator(
                    size = 30.dp,
                    strokeWidth = 4.dp,
                    paddingTop = 0.dp, // bỏ padding để căn giữa dialog
                    contentAlignment = Alignment.Center
                )
            }
        }
    }
}
