package com.contrast.Contrast.presentation.features.flashSale.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable

import com.contrast.Contrast.presentation.theme.FFFF9800

@Composable
fun FlashSaleSeeAllCard(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(90.dp)
            .height(160.dp) // 👈 đảm bảo cùng chiều cao với item bên cạnh
            .noRippleClickableComposable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.next_flash_sale),
            contentDescription = null,
            colorFilter = ColorFilter.tint(FFFF9800),
            modifier = Modifier
                .size(45.dp)
                .padding(10.dp)
        )

        Text(
            text = stringResource(id = R.string.see_all),
            fontSize = 11.sp,
            color = FFFF9800,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            minLines = 2
        )
    }}
}
