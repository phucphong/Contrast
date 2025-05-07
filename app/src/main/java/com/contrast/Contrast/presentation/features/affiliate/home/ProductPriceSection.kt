package com.contrast.Contrast.presentation.features.affiliate.home

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.progressBar.FlashSaleSeekBar
import com.contrast.Contrast.presentation.features.product.ui.PriceBar
import com.contrast.Contrast.presentation.features.product.ui.PromoPriceBar
import com.itechpro.domain.model.PromoUiData

@Composable
fun ProductPriceSection(
    promoUiData: PromoUiData?,
    price: Double,
    promoPrice: Double,
    isShare: Boolean,
    onClickCart: () -> Unit,
    onClickAddServiceRequest: () -> Unit,
    onClickShare: () -> Unit
) {
    if (promoUiData != null) {
        val countdownText by rememberUpdatedState(promoUiData.remainingTime)
        val progress by rememberUpdatedState(promoUiData.progress)
        Log.e("countdownText",countdownText)
        if(countdownText!="00:00:00"){
            FlashSaleSeekBar(
                progress = progress,
                remainingTime = countdownText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )
            PromoPriceBar(price = promoPrice.formatCurrency())
        }else{
            PriceBar(
                price = price.formatCurrency(),
                isShare = isShare,
                onClickCart = onClickCart,
                onClickAddServiceRequest = onClickAddServiceRequest,
                onClickShare = onClickShare,
            )

        }



    } else {
        PriceBar(
            price = price.formatCurrency(),
            isShare = isShare,
            onClickCart = onClickCart,
            onClickAddServiceRequest = onClickAddServiceRequest,
            onClickShare = onClickShare,
        )
    }
}
