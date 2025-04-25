package com.contrast.Contrast.presentation.features.review.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import com.itechpro.domain.model.review.ReviewFilter
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
@Composable
fun ReviewStarFilterRow(
    selected: Int?,
    starCounts: Map<Int, Int>,
    onSelect: (Int?) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(horizontal = 8.dp)) {
        (5 downTo 1).forEach { star ->
            val count = starCounts[star] ?: 0
            val isSelected = selected == star
            item {
                Column(
                    modifier = Modifier
                        .width(64.dp)
                        .height(56.dp)
                        .clickable { onSelect(if (isSelected) null else star) }
                        .background(Color(0xFFF7F7F7), RoundedCornerShape(6.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        repeat(5) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,

                                modifier = Modifier.height(12.dp).width(12.dp),
                                tint = if (it < star) Color.Black else Color.LightGray
                            )
                        }
                    }
                    Text("($count)", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                }
            }
        }
    }
}
