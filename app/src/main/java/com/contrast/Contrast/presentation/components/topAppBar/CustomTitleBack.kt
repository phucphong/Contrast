package com.contrast.Contrast.presentation.components.topAppBar


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R

@Composable
fun CustomTitleBack(
    title: String,
    tint:Color = Color.Gray,
    onBackPress: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Tiêu đề màn hình
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )

        // Nút đóng
        IconButton(
            onClick = onBackPress
        ) {
            Icon(
                painter = painterResource(id = R.drawable.close_2),
                contentDescription ="",
                tint = tint
            )
        }
    }
}
