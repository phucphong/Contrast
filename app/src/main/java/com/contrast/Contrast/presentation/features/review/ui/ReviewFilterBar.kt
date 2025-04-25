package com.contrast.Contrast.presentation.features.review.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import com.itechpro.domain.model.review.ReviewFilter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp

@Composable
fun ReviewFilterBar(
    selected: ReviewFilter,
    ratingCountMap: Map<Int, Int>,
    onFilterSelected: (ReviewFilter) -> Unit
) {
    Column {
        Row(modifier = Modifier.padding(8.dp)) {
            listOf(
                "Tất cả" to ReviewFilter.All,
                "Kèm bình luận" to ReviewFilter.CommentOnly,
                "Kèm hình ảnh" to ReviewFilter.ImageOnly
            ).forEach { (label, filter) ->
                FilterChip(
                    label = "$label (${getCount(filter, ratingCountMap)})",
                    isSelected = selected == filter,
                    onClick = { onFilterSelected(filter) }
                )
            }
        }

        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
            (5 downTo 1).forEach { star ->
                val filter = ReviewFilter.Star(star)
                FilterChip(
                    label = "★".repeat(star) + " (${ratingCountMap[star] ?: 0})",
                    isSelected = selected == filter,
                    onClick = { onFilterSelected(filter) }
                )
            }
        }
    }
}

@Composable
fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        androidx.compose.material3.Text(
            text = label,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

fun getCount(filter: ReviewFilter, map: Map<Int, Int>): Int = when (filter) {
    is ReviewFilter.All -> map.values.sum()
    is ReviewFilter.CommentOnly -> 1 // cập nhật thật từ ViewModel nếu có
    is ReviewFilter.ImageOnly -> 0
    is ReviewFilter.Star -> map[filter.star] ?: 0
}
