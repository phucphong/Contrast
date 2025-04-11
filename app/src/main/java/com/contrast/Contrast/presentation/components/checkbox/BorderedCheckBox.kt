package com.contrast.Contrast.presentation.components.checkbox

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

@Composable
fun BorderedCheckBox(
    size: Dp=22.dp,
    checked: Boolean,
    bordercolor: Color=Color.Gray,
    tintIcon: Color=Color.Gray,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .border(
                width = 1.5.dp,
                color = bordercolor,
                shape = RoundedCornerShape(4.dp)
            )
            .noRippleClickableComposable {  onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            // Icon tick hoặc khối màu
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = tintIcon,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
