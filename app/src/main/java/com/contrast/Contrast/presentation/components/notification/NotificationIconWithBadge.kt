package com.contrast.Contrast.presentation.components.notification


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R

import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

import com.contrast.Contrast.presentation.theme.TealGreen

@Composable
fun NotificationIconWithBadge(
    count: Int,
    onNotificationClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .padding(5.dp)
            .noRippleClickableComposable { onNotificationClick() } // 👈 dùng hàm extension của bạn
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = "Cart",
            modifier = Modifier
                .align(Alignment.Center)
                .size(30.dp).padding(5.dp),
            colorFilter = ColorFilter.tint(TealGreen)
        )

        if (count > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 1.dp, y = (-1).dp)
                    .size(16.dp)
                    .background(Color.Red, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = count.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
