package com.contrast.Contrast.presentation.components.progressBar


import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PromoProgressBar(

) {
    Box(
        modifier = Modifier
            .width(80.dp)
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.Transparent),
        contentAlignment = Alignment.Center

    ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(6.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color.Yellow, Color(0xFFFF9800)) // vàng → cam
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
        )

        Row {
            Box (modifier = Modifier.weight(1f))

            Image(
                painter= painterResource(R.drawable.clock_flash_sales),
                contentDescription = "Time",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(16.dp)
            )
            Box (modifier = Modifier.width(15.dp))
        }
    }




}
