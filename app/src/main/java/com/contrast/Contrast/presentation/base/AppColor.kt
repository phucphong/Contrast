package com.contrast.Contrast.presentation.base



import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import com.contrast.Contrast.presentation.theme.AEA1F27
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.TealGreen

val AppColor = lightColors(
    primary = TealGreen,        // màu chính
    primaryVariant = TealGreen, // màu variant
    secondary = AEA1F27,        // màu phụ
    background = Color.White,   // nền
    surface = Color.White,      // màu thẻ surface
    onPrimary = Color.White,    // chữ trên màu primary
    onSecondary = Color.Black,  // chữ trên màu secondary
    onBackground = FF151515,    // chữ trên nền
    onSurface = FF151515        // chữ trên surface
)
