package com.contrast.Contrast.presentation.components.progressBar


import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    progress: Float,
    remainingTime: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
    ) {
        // 🕒 Icon đồng hồ
        Icon(
            painter = painterResource(id = R.drawable.clock_flash_sales), // icon đồng hồ
            contentDescription = "Clock",
            tint = Color(0xFFFF9800),
            modifier = Modifier
                .size(16.dp)
                .padding(end = 4.dp)
        )

        // 🔶 Thanh tiến trình cam
        Box(
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE0E0E0)) // nền xám nhạt
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(Color(0xFFFF9800)) // cam đậm
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        // ⏱ Text countdown
        Text(
            text = remainingTime,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFFF9800)
        )
    }
}
