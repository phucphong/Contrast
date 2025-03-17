package com.contrast.Contrast.presentation.components.topAppBar

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(title: String, color: Color = Color.Black,onBackClick: () -> Unit) {
    TopAppBar(
        title = {
            CustomText(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Red
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}
