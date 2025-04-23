package com.contrast.Contrast.presentation.features.product.detail.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
@Preview(showBackground = true)
@Composable
fun ProductDetailHeader(
    onSaveClick: () -> Unit,
    onReportClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Chi tiết sản phẩm",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Lưu",
                modifier = Modifier
                    .size(20.dp)
                    .noRippleClickableComposable { onSaveClick() }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Lưu",
                modifier = Modifier.noRippleClickableComposable { onSaveClick() },
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "|",
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Báo cáo",
                modifier = Modifier.noRippleClickableComposable { onReportClick() },
                fontSize = 14.sp
            )
        }
    }
}
