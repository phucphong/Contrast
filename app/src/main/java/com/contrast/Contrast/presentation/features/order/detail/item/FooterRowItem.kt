package com.contrast.Contrast.presentation.features.order.detail.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FooterRowItem(label: String, value: Double?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label.uppercase(),
            fontWeight = FontWeight.Bold, fontSize = 13.sp,
            color = Color.Gray,
            textAlign = TextAlign.Right, modifier = Modifier.weight(1f))
        Text(
            text = "₫${value?.toLong()?.toString() ?: "0"}",
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,textAlign = TextAlign.Right,
            color = Color.Black, modifier = Modifier.weight(1f)
        )
    }
}