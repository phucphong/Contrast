package com.contrast.Contrast.presentation.features.product.ui

import com.contrast.Contrast.presentation.components.text.CustomText



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
import com.contrast.Contrast.presentation.theme.FFFF5722
import com.contrast.Contrast.presentation.theme.PlaceholderGray


@Composable
fun EarnProduct(
    commissionMoney: Double,
    commissionRate: Double,
    coin: Double,
    pointAffiliate: String,
    modifier: Modifier = Modifier
) {


    var  commission =  ""
    if (pointAffiliate == "0"){
        commission = "${commissionRate.formatDouble()}% ~ ${commissionMoney.formatCurrency()}"
    }

    Row(modifier = modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        CustomText(
            text = "${stringResource(R.string.commission)}:",
            color = PlaceholderGray,
            fontSize = 10.sp,
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 5.dp)
        )
        CustomText(
            text = if (pointAffiliate == "0") commission else coin.formatDouble(),
            color = if (pointAffiliate == "1")  PlaceholderGray else FFFF5722,
            fontSize = 10.sp,
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 3.dp, end = 1.dp)
        )
        if ( pointAffiliate == "1") {
            Image(
                painter = painterResource(R.drawable.coin_product),
                contentDescription = "coin_product",
                modifier = Modifier.size(15.dp)
            )
        }

    }
}
