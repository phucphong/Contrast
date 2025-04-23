package com.contrast.Contrast.presentation.features.flashSale.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.contrast.Contrast.R

import com.contrast.Contrast.presentation.components.text.CustomText

@Composable
fun FlashSaleHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.flash_sale),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier.size(30.dp).padding(10.dp, 7.dp, 7.dp, 5.dp)
        )
        CustomText(
            text = stringResource(id = R.string.flash_sale),
            fontSize = 16.sp,
            paddingTop=5.dp
        )
    }
}
