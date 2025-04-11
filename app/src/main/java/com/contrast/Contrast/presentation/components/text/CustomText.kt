package com.contrast.Contrast.presentation.components.text

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDivider
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.theme.FF000000
import com.contrast.Contrast.presentation.theme.PlaceholderGray

@Composable
fun CustomText(
    text: AnnotatedString,
    fontWeight: FontWeight =  FontWeight(400),
    fontSize: TextUnit = 14.sp,
    color: Color = Color.Black,
    colorUnderline: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Left,
    showUnderline: Boolean = false,
            weight: Boolean = false
) {
    val customFontFamily = FontFamily(
        Font(R.font.inter)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        if (showUnderline) {
            Spacer(modifier = Modifier.height(6.dp))
        }

        Text(
            text = text,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = fontWeight,
                fontSize = fontSize,
                color = color,
                textAlign = textAlign,

            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
                .let {
                    if (weight) it.weight(1f)
                    else it
                }

        )

        if (showUnderline) {
            CustomDividerColor(PlaceholderGray)
        }
    }
}

@Composable
fun CustomText(
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 14.sp,

    color: Color = FF000000,
    textAlign: TextAlign = TextAlign.Left,
    showUnderline: Boolean = false,
    weight: Boolean = false,
) {
    CustomText(
        text = AnnotatedString(text),
        fontWeight = fontWeight,
        fontSize = fontSize,
        color = color,
        textAlign = textAlign,
        showUnderline = showUnderline
    )
}
