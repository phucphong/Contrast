package com.contrast.Contrast.presentation.components.text


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FF000000

private val defaultFontFamily = FontFamily(Font(R.font.inter))

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 14.sp,
    color: Color = FF000000,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    isLowercase: Boolean = false
) {
    val displayText = if (isLowercase) text.lowercase() else text
    Text(
        text = displayText,
        style = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = fontWeight,
            fontSize = fontSize,
            color = color,
            textAlign = textAlign
        ),
        maxLines = maxLines,
        modifier = modifier
    )
}

@Composable
fun CustomText(
    text: AnnotatedString,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp),
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 14.sp,
    color: Color = FF000000,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = defaultFontFamily,
            fontWeight = fontWeight,
            fontSize = fontSize,
            color = color,
            textAlign = textAlign
        ),
        maxLines = maxLines,
        modifier = modifier
    )
}
