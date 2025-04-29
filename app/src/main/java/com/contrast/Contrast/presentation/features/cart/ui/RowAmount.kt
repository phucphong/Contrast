package com.contrast.Contrast.presentation.features.cart.ui

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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.text.CustomText

@Composable
fun RowAmount(label: String,discount: String, amount: String, isBold: Boolean = false,textColor:Color= Color.Black) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 6.dp, start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp, textAlign = TextAlign.Right
        )
        Row {
            Text(
            text = "${discount}đ",
                color = textColor,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
                modifier = Modifier.padding(top = 2.dp)

        )
            Text(
                text = amount,
                color = textColor,
                fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
                fontSize = 14.sp
            )
        }
    }
}
