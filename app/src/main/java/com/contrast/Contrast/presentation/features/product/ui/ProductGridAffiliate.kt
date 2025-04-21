package com.contrast.Contrast.presentation.features.product.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductGridAffiliate(
    domain: String,
    products: List<Product>,
    promoUiDataMap: Map<String, PromoUiData> = emptyMap(), // ✅ truyền vào để tối ưu countdown
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
        items(products, key = { it.id?:"" }) { product ->

              ProductCardAffiliate(
                  domain = domain,
                  product = product,
                  promoUiData = promoUiDataMap[product.id], // ✅ truyền countdown riêng
                  onClick = { onItemClick(product) },
                  onClickCart = { onClickCart(product) },
                  onClickAddServiceRequest = { onClickAddServiceRequest(product) },


                  )

        }
    }
}
