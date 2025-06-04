package com.contrast.Contrast.presentation.components.successLabel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.text.CustomText


@Composable
fun SuccessLabel(
    status: String = "", color: String = "#1bb635"
) {
    val safeColor = remember(color) {
        try {
            Color(android.graphics.Color.parseColor(color))
        } catch (e: Exception) {
            Color(0xFF1bb635) // fallback màu xanh lá nếu lỗi
        }
    }

    Column(
        Modifier

            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .background(color = safeColor, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            CustomText(
                text = status,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight(500),
                modifier = Modifier
                    .wrapContentSize()
                    .padding(3.dp)
            )
        }
    }

}
