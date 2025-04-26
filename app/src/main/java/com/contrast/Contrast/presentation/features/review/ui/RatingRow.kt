package com.contrast.Contrast.presentation.features.review.ui
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.contrast.Contrast.presentation.features.review.ReviewViewModel
import com.contrast.Contrast.presentation.theme.FFD7D7D7
@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RatingRow(
    rating: Int,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val starSize: Dp = 16.dp
    val spacing = 4.dp
    val totalStars = 5

    // ✅ Tách LocalDensity ra để tránh lỗi
    val density = LocalDensity.current
    val starWidthPx = remember(density) {
        with(density) { (starSize + spacing).toPx() }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .pointerInput(starWidthPx) {
                detectTapGestures { offset ->
                    val tappedStar = ((offset.x) / starWidthPx).toInt() + 1
                    val safeStar = tappedStar.coerceIn(1, totalStars)
                    viewModel.setRatingNote(safeStar)
                }
            }
    ) {
        (1..totalStars).forEach { star ->
            Icon(
                imageVector = if (star <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Star $star",
                tint = if (star <= rating) Color(0xFFFFC107) else Color.Gray,
                modifier = Modifier
                    .size(starSize)
                    .clickable { viewModel.setRatingNote(star) }
            )
        }
    }
}
