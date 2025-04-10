package com.contrast.Contrast.presentation.components.checkbox
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.FF404040


@Composable
fun CheckBoxColor(
    checked: Boolean,
    padding: Dp =10.dp,
    background: Color=Color.Black,
    onCheckedChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier.padding(padding)
            .size(24.dp)
            .then(
                if (checked) Modifier.background(background, RoundedCornerShape(4.dp))
                else Modifier
                    .border(1.5.dp,FF404040, RoundedCornerShape(4.dp))
                    .background(Color.White, RoundedCornerShape(4.dp))
            )
            .noRippleClickableComposable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
