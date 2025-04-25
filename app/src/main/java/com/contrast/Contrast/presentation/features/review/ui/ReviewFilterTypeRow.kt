package com.contrast.Contrast.presentation.features.review.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import com.itechpro.domain.model.review.ReviewFilter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import com.itechpro.domain.enumApp.ReviewFilterType

@Composable
fun ReviewFilterTypeRow(
    selected: ReviewFilterType,
    counts: Map<ReviewFilterType, Int>,
    onSelect: (ReviewFilterType) -> Unit
) {
    val options = listOf(
        ReviewFilterType.ALL to "Tất cả",
        ReviewFilterType.COMMENT_ONLY to "Kèm bình luận",
        ReviewFilterType.IMAGE_ONLY to "Kèm hình ảnh"
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(horizontal = 8.dp)) {
        items(options) { (type, label) ->
            val count = counts[type] ?: 0
            Column(
                modifier = Modifier
                    .width(96.dp)
                    .height(56.dp)
                    .clickable { onSelect(type) }
                    .border(
                        1.dp,
                        if (selected == type) Color(0xFF00BCD4) else Color.Transparent,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .background(Color(0xFFF7F7F7), RoundedCornerShape(6.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (selected == type) Color(0xFF00BCD4) else Color.DarkGray
                )
                Text(
                    text = "($count)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
            }
        }
    }
}
