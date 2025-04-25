package com.contrast.Contrast.presentation.components.button


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

import com.contrast.Contrast.presentation.theme.FFD91E18


@Composable
fun CustomButton(text:String, fontWeight: FontWeight =  FontWeight(600),
                 fontSize: TextUnit = 14.sp,
                 modifier: Modifier = Modifier,
                 enabled: Boolean = true,

                 roundedCornerShape: Dp = 16.dp,
                 textColor: Color = Color.White,
                 containerColor: Color = FFD91E18, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        enabled=enabled,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        modifier = modifier,
        shape = RoundedCornerShape(roundedCornerShape)
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
                    style = TextStyle(
                    fontSize = fontSize,
            lineHeight = 17.5.sp,
            fontFamily = FontFamily(Font(R.font.inter)),
            fontWeight =fontWeight,
            color = textColor,


        )
        )
    }

}