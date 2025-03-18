package com.contrast.Contrast.presentation.components.topAppBar

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBarTextImage(title: String, color: Color = Color.Black,background : Color = Color.White,onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            CustomText(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color,
            )
        },
        modifier = Modifier.background(background),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Gray
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}
