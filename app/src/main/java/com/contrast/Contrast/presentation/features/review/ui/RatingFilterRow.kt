package com.contrast.Contrast.presentation.features.review.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

@Composable
fun RatingFilterRow(ratingCounts: Map<Int, Int>, selectedStar: Int?, onSelect: (Int?) -> Unit) {
    Row(modifier = Modifier.padding(horizontal = 8.dp)) {
        (5 downTo 1).forEach { star ->
            val count = ratingCounts[star] ?: 0
            val selected = selectedStar == star
            Box(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .noRippleClickableComposable { onSelect(star) }
                    .border(
                        width = 1.dp,
                        color = if (selected) MaterialTheme.colorScheme.primary else Color.LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = "★".repeat(star) + " ($count)", fontSize = 12.sp)
            }
        }
    }
}
