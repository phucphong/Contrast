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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.theme.FFD91E18


@Composable
 fun CustomButton(text:String, fontWeight: FontWeight =  FontWeight(400),
               fontSize: TextUnit = 14.sp,
               textColor: Color = Color.White,
               containerColor: Color = FFD91E18, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(24.dp))
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp).padding(horizontal = 10.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = fontSize
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
}