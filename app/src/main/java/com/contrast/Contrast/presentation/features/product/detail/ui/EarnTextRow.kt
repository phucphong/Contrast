package com.contrast.Contrast.presentation.features.product.detail.ui


import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.presentation.components.text.CustomText


@Composable
fun EarnTextRow(
    commissionMoney: Double,
    commissionRate: Double,
    coin: Double,
    pointAffiliate: String,
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier.padding(10.dp)){
    Row( verticalAlignment = Alignment.CenterVertically) {
        CustomText(
            text = stringResource(R.string.earn),
            color = Color.Black,
            fontSize = 13.sp,
            modifier = Modifier
                .wrapContentWidth()

        )
        CustomText(
            text = if (pointAffiliate == "0") commissionMoney.formatCurrency() else coin.formatDouble(),
            color = Color.Red,
            fontSize = 13.sp,
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 5.dp)
        )
        if (coin > 0 && pointAffiliate == "0") {
            Image(
                painter = painterResource(R.drawable.coin_product),
                contentDescription = "coin_product",
                modifier = Modifier.size(15.dp)
            )
        }
        CustomText(
            text = stringResource(R.string.perSale),
            color = Color.Black,
            fontSize = 13.sp,
            modifier = Modifier.wrapContentWidth()
        )
    }
        if (pointAffiliate == "0") {
            CustomText(
                text = "${commissionRate.formatDouble()}% ${stringResource(R.string.commission)}",
                color = Color.Black,
                fontSize = 13.sp,
                isLowercase = true,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 5.dp )

            )
        }
    }
}
