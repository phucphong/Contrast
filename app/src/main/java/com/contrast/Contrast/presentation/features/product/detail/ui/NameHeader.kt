package com.contrast.Contrast.presentation.features.product.detail.ui



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
fun NameHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(Color.White)
    ) {

        CustomText(
            text = stringResource(id = R.string.flash_sale),
            fontSize = 16.sp,
            paddingStart =15.dp,
            paddingTop=5.dp,
        )
    }
}
