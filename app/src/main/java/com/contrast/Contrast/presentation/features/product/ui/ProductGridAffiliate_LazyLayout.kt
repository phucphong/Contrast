package com.contrast.Contrast.presentation.features.product.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.layout.LazyLayout
import androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider
import androidx.compose.foundation.lazy.layout.LazyLayoutMeasureScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.unit.Constraints
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductGridAffiliate_LazyLayout(
    domain: String,
    products: List<Product>,
    promoUiDataMap: Map<String, PromoUiData> = emptyMap(),
    onItemClick: (Product) -> Unit,
    onClickCart: (Product) -> Unit,
    onClickAddServiceRequest: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    // Item Provider
    val itemProvider = remember(products, promoUiDataMap) {
        object : LazyLayoutItemProvider {
            override val itemCount: Int = products.size

            @Composable
            override fun Item(index: Int, key: Any) {
                ProductCardAffiliate(
                    domain = domain,
                    product = products[index],
                    promoUiData = promoUiDataMap[products[index].id],
                    onClick = { onItemClick(products[index]) },
                    onClickCart = { onClickCart(products[index]) },
                    onClickAddServiceRequest = { onClickAddServiceRequest(products[index]) }
                )
            }
        }
    }

    // MeasurePolicy
    val measurePolicy = remember(products.size) {
        val itemCount = products.size

        fun LazyLayoutMeasureScope.gridMeasure(constraints: Constraints): MeasureResult {
            val columns = 2
            val itemWidth = constraints.maxWidth / columns
            val itemConstraints = constraints.copy(
                minWidth = itemWidth,
                maxWidth = itemWidth
            )

            val placeables = (0 until itemCount).map { index ->
                measure(index, itemConstraints)
            }.flatten()

            val rows = (placeables.size + columns - 1) / columns
            val rowHeights = IntArray(rows) { row ->
                (0 until columns).mapNotNull { col ->
                    placeables.getOrNull(row * columns + col)?.height
                }.maxOrNull() ?: 0
            }

            val totalHeight = rowHeights.sum()

            return layout(constraints.maxWidth, totalHeight) {
                var yOffset = 0
                for (row in 0 until rows) {
                    val rowHeight = rowHeights[row]
                    for (col in 0 until columns) {
                        val index = row * columns + col
                        placeables.getOrNull(index)?.place(
                            x = col * itemWidth,
                            y = yOffset
                        )
                    }
                    yOffset += rowHeight
                }
            }
        }

        LazyLayoutMeasureScope::gridMeasure
    }


    // UI
    Box(modifier = modifier.verticalScroll(rememberScrollState())) {
        LazyLayout(
            itemProvider = { itemProvider },
            modifier = Modifier.fillMaxWidth(),
            measurePolicy = measurePolicy
        )
    }
}
