package com.contrast.Contrast.presentation.components.ratingbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable


@Composable
fun RatingStars(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    starSize: Dp = 16.dp
) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = if (index < rating) Color(0xFFFFC107) else Color.LightGray,
                modifier = Modifier
                    .width(starSize)
                    .height(starSize)
                    .noRippleClickableComposable { onRatingChanged(index + 1) }
            )
        }
    }
}
