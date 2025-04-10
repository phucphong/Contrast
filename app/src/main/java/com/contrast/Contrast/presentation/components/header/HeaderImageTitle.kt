package com.contrast.Contrast.presentation.components.header

import androidx.compose.foundation.Image
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.unit.dp

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FAFAFA


@Composable
fun HeaderImageTitle(
    name: String,
    modifier: Modifier = Modifier,

    iconRes: Int = R.drawable.logo // bạn có thể thay bằng icon mặc định
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(FAFAFA),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(50.dp).padding(10.dp),

        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,modifier = Modifier.wrapContentWidth().padding(10.dp),
        )
    }
}
