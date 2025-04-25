package com.contrast.Contrast.presentation.features.review.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itechpro.domain.enumApp.ReviewFilterType
import com.itechpro.domain.enumApp.ReviewSelectedFilter

@Composable
fun ReviewFilterSection(
    selectedFilter: ReviewSelectedFilter,
    filterCounts: Map<ReviewFilterType, Int>,
    starCounts: Map<Int, Int>,
    onFilterSelect: (ReviewFilterType) -> Unit,
    onStarSelect: (Int) -> Unit
) {
    val selectedType = (selectedFilter as? ReviewSelectedFilter.Type)?.type
    val selectedStar = (selectedFilter as? ReviewSelectedFilter.Star)?.star

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Dòng 1: Lọc theo loại (chia đều 3 phần)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val options = listOf(
                ReviewFilterType.ALL to "Tất cả",
                ReviewFilterType.COMMENT_ONLY to "Kèm bình luận",
                ReviewFilterType.IMAGE_ONLY to "Kèm hình ảnh"
            )

            options.forEach { (type, label) ->
                val count = filterCounts[type] ?: 0
                val isSelected = selectedType == type

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clickable { onFilterSelect(type) }
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color(0xFF00BCD4) else Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isSelected) Color(0xFF00BCD4) else Color.Black
                    )
                    Text(
                        text = "($count)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )
                }
            }
        }

        // Dòng 2: Lọc theo số sao (chia đều 5 phần)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (5 downTo 1).forEach { star ->
                val count = starCounts[star] ?: 0
                val isSelected = selectedStar == star

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .clickable { onStarSelect(star) }
                        .border(
                            width = 1.dp,
                            color = if (isSelected) Color(0xFF00BCD4) else Color.LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(Color.White, shape = RoundedCornerShape(8.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(5) { index ->
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = if (index < star) Color.Black else Color.LightGray,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                    Text(
                        text = "($count)",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )
                }
            }
        }
    }
}
