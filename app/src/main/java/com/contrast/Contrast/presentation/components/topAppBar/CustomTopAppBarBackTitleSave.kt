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
import com.contrast.Contrast.presentation.components.text.CustomText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBarBackTitleSave(
    title: String,
    titleColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Bold,
    iconTint: Color = Color.Gray,
    onBackClick: () -> Unit,
    onSaveClick: (() -> Unit)? = null // 👈 Thêm action Save
) {
    TopAppBar(
        title = {
            CustomText(
                text = title,
                fontSize = 20.sp,
                fontWeight = fontWeight,
                color = titleColor,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = iconTint,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        },
        actions = {
            onSaveClick?.let {
                TextButton(onClick = it) {
                    Text(
                        text = "Lưu",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundColor
        )
    )
}

