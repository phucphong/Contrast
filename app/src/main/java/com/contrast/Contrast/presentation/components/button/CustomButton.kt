package com.contrast.Contrast.presentation.components.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
                 roundedCornerShape: Dp = 16.dp,
                 paddingStart: Dp = 16.dp,
                 paddingTop: Dp = 40.dp,
                 paddingEnd: Dp = 16.dp,
                 paddingBottom: Dp = 16.dp,

                 textColor: Color = Color.White,
                 containerColor: Color = FFD91E18, onClick: () -> Unit) {

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight().padding( paddingStart, paddingTop, paddingEnd, paddingBottom),
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