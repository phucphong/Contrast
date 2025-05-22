package com.contrast.Contrast.presentation.components.topAppBar


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

@Composable
fun CustomBackTitle(
    title: String,
    tint: Color = Color.Gray,
    textColor: Color = Color.Red,
    background: Color = Color.White,
    fontSize: TextUnit = 14.sp,
    painter: Painter = painterResource(id = R.drawable.back),
    onBackPress: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(background)
    ) {
        // Nút Back ở bên trái
        Image(
            painter = painter,
            contentDescription = "",
            colorFilter = ColorFilter.tint(tint),



            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(55.dp)
                .padding(10.dp).noRippleClickableComposable { onBackPress() }
        )

        // Tiêu đề căn giữa tuyệt đối
        Text(
            text = title,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
