package com.contrast.Contrast.presentation.components.topAppBar


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.TealGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBarBackTitleSave(
    title: String,
    fontSize: TextUnit = 14.sp,
    titleColor: Color = Color.Black,
    backgroundColor: Color = Color.White,
    fontWeight: FontWeight = FontWeight.Bold,
    iconTint: Color = Color.Gray,
    isSave:Boolean =true,
    onBackClick: () -> Unit,
    onSaveClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        title = {
            CustomText(
                text = title,
                fontSize = fontSize,
                fontWeight = fontWeight,
                color = titleColor, textAlign = TextAlign.Center
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
            if (isSave && onSaveClick != null) {
                TextButton(onClick = onSaveClick) {
                    Text(
                        text = stringResource(R.string.save),
                        color = TealGreen,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
        ,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor
        )
    )
}
