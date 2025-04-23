package com.contrast.Contrast.presentation.features.cart.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.text.CustomText

@Composable
fun RowAmount(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomText(text = label,textAlign= TextAlign.Right)
        CustomText(
            text = value,
            textAlign= TextAlign.Right,

            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}
