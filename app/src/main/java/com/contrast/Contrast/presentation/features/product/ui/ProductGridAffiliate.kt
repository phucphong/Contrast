package com.contrast.Contrast.presentation.features.product.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.PromoUiData
import kotlinx.coroutines.flow.StateFlow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductGridAffiliate(
    domain: String,
    products: List<Product>,
    promoUiDataMap: Map<String, StateFlow<PromoUiData>> = emptyMap(),
    onItemClick: (Product) -> Unit,
    onClickCart: (Product) -> Unit,
    onClickAddServiceRequest: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        products.chunked(2).forEach { rowProducts ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (product in rowProducts) {
                    ProductCardAffiliate(
                        domain = domain,
                        product = product,
                        promoUiDataFlow = promoUiDataMap[product.id],
                        onClick = { onItemClick(product) },
                        onClickCart = { onClickCart(product) },
                        onClickAddServiceRequest = { onClickAddServiceRequest(product) },

                    )
                }

                // Nếu số lượng lẻ, chèn ô trống để cân layout
                if (rowProducts.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
