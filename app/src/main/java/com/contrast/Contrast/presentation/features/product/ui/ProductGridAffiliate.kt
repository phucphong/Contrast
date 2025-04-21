package com.contrast.Contrast.presentation.features.product.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import kotlinx.coroutines.flow.StateFlow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductGridAffiliate(
    domain: String,
    products: List<Product>,
    promoUiDataMap: Map<String, StateFlow<PromoUiData>> = emptyMap(), // ✅ truyền StateFlow thay vì value
    onItemClick: (Product) -> Unit,
    onClickCart: (Product) -> Unit,
    onClickAddServiceRequest: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(products, key = { it.id.orEmpty() }) { product ->
            ProductCardAffiliate(
                domain = domain,
                product = product,
                promoUiDataFlow = promoUiDataMap[product.id], // ✅ truyền flow từng item
                onClick = { onItemClick(product) },
                onClickCart = { onClickCart(product) },
                onClickAddServiceRequest = { onClickAddServiceRequest(product) },
            )
        }
    }
}
