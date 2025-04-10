package com.contrast.Contrast.presentation.components.topAppBar



import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBarBackTitle(title: String,
                             titleColor: Color = Color.Black,


                             backgroundColor : Color = Color.White, fontWeight :FontWeight= FontWeight.Bold,
                             iconTint:Color = Color.Gray,  fontSize: TextUnit =20.sp,onBackClick: () -> Unit) {
    TopAppBar(
        title = {

            CustomText(
                text = title,
                fontSize = fontSize,
                fontWeight = fontWeight,
                color = titleColor,
            )
        },

        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),

    )
}
