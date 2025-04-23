package com.contrast.Contrast.presentation.features.flashSale
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp

import com.contrast.Contrast.presentation.features.flashSale.ui.FlashSaleHeader
import com.contrast.Contrast.presentation.features.flashSale.ui.FlashSaleItemCard
import com.contrast.Contrast.presentation.features.flashSale.ui.FlashSaleSeeAllCard
import com.contrast.Contrast.presentation.theme.FFFF9800
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import kotlinx.coroutines.flow.StateFlow


@Composable
fun FlashSaleHome(
    flashSales: List<Product>,
    promoUiDataMap: Map<String, StateFlow<PromoUiData>>,
    domain: String,
    onItemProductSelected: (Product) -> Unit,
    onSeeAllClicked: () -> Unit
) {
    val itemsToShow = flashSales.take(4)

    Column {
        // 🔸 Header


        // 🔸 Flash Sale Items + "Xem tất cả"
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(itemsToShow.size + 1) { index ->
                if (index < itemsToShow.size) {
                    val item = itemsToShow[index]
                    FlashSaleItemCard(
                        product = item,
                        promoUiDataFlow = promoUiDataMap[item.id],
                        domain = domain,
                        onClick = { onItemProductSelected(item) }
                    )
                } else {
                    FlashSaleSeeAllCard(onClick = onSeeAllClicked)
                }
            }
        }
    }
}
