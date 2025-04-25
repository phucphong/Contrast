package com.contrast.Contrast.presentation.features.flashSale.ui

import android.util.Log
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.image.NetworkImage

import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.progressBar.FlashSaleSeekBar

import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import kotlinx.coroutines.flow.StateFlow
@Composable
fun FlashSaleItemCard(
    product: Product,
    promoUiDataFlow: StateFlow<PromoUiData>?, // 👈 mỗi item có 1 Flow riêng
    domain: String,
    onClick: () -> Unit
) {
    val name = product.ten.orEmpty()
    val fullUrl = domain.trimEnd('/') + (product.filetxt ?: "")
    val promoUiData by promoUiDataFlow?.collectAsState() ?: remember { mutableStateOf(PromoUiData()) }
    val progress by rememberUpdatedState(promoUiData.progress)

    Log.e("progress",progress.toString())
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(90.dp)
            .noRippleClickableComposable { onClick() }
    ) {
        NetworkImage(
            model = fullUrl,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .width(90.dp)
                .height(100.dp)

        )

        Text(
            text = name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp),
            maxLines = 1
        )

        FlashSaleSeekBar(
            progress = progress,
            remainingTime = promoUiData.remainingTime,
            isRemainingTime = false,
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}
