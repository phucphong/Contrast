package com.contrast.Contrast.presentation.features.affiliate.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.progressBar.FlashSaleProductSeekBar
import com.contrast.Contrast.presentation.components.progressBar.FlashSaleSeekBar

import com.contrast.Contrast.presentation.features.product.ui.PromoPriceBar
import com.itechpro.domain.model.PromoUiData

@Composable
fun ProductPriceSection(
    promoUiData: PromoUiData?,
    price: Double,
    promoPrice: Double,
    isShare: Boolean,
    bookService: Boolean,
    onClickCart: () -> Unit,
    onClickAddServiceRequest: () -> Unit,
    onClickShare: () -> Unit
) {

    val countdownText by rememberUpdatedState(promoUiData?.remainingTime)
    val progress by rememberUpdatedState(promoUiData?.progress)

    var isFlashSale by remember { mutableStateOf(false) }


    LaunchedEffect(countdownText) {
        if (countdownText == "00:00:00:00") {
            isFlashSale = false
        }else{
            isFlashSale = true
        }
    }
    FlashSaleProductSeekBar(
        progress = progress?:0f,
        remainingTime = countdownText?:"",
        isFlashSale = isFlashSale,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    )
    PromoPriceBar(price = if(isFlashSale)promoPrice.formatCurrency() else price.formatCurrency(), isShare = isShare,
        isFlashSale = isFlashSale,
        onClickCart = onClickCart,
        bookService = bookService,
        onClickAddServiceRequest = onClickAddServiceRequest,
        onClickShare = onClickShare)

}
