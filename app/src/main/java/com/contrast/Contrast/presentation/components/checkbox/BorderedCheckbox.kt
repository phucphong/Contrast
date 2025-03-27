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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

@Preview(showBackground = true)
@Composable
fun BorderedCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .border(
                width = 1.5.dp,
                color = Color.Gray,
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
                tint = Color.Black,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
