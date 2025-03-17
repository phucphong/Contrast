package com.contrast.Contrast.presentation.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

@Composable
fun CustomText(
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 14.sp,
    color: Color = Color.Black,
    textAlign: TextAlign = TextAlign.Left,
    showUnderline: Boolean = false // Thêm tùy chọn hiển thị gạch chân
) {
    // Khai báo fontFamily
    val customFontFamily = FontFamily(
        Font(R.font.inter_18pt_medium), // Font thường
        Font(R.font.inter_18pt_bold, FontWeight.Bold),
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        // Text với font và kiểu chữ tùy chỉnh
        if (showUnderline) {
            Spacer(modifier = Modifier.height(6.dp))
        }
        Text(
            text = text,
            style = TextStyle(
                fontFamily = customFontFamily, // Thiết lập fontFamily
                fontWeight = fontWeight,  // Chỉnh sửa trọng lượng chữ (FontWeight)
                fontSize = fontSize,  // Kích thước chữ
                color = color, // Màu chữ
                textAlign = textAlign,
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
        )

        // Hiển thị dòng gạch chân bên dưới nếu `showUnderline` = true

        if (showUnderline) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray.copy(alpha = 0.8f), shape = RoundedCornerShape(50))
            )
        }
    }
}
