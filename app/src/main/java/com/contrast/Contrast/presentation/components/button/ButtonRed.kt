package com.contrast.Contrast.presentation.components.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FFD91E18


@Composable
 fun ButtonRed(text:String,onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(24.dp))
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = FFD91E18),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp).padding(horizontal = 10.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}