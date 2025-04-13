package com.contrast.Contrast.presentation.features.product.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

@Composable
fun PromoPriceBar(
    price: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(40.dp).padding(5.dp)
            .clip(RoundedCornerShape(50)) // full bo tròn
            .background(Color(0xFFFFF3E0)) // nền cam nhạt
    ) {
        // Giá (bên trái)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = price,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800)
            )
        }

        // Cột icon bên phải (nền cam đậm)
        Row(
            modifier = Modifier
                .background(Color(0xFFFF9800))
                .fillMaxHeight()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = "Cart",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.calendar_service),
                contentDescription = "Buy Package",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

