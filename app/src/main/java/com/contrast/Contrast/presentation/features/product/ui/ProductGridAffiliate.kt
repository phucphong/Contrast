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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itechpro.domain.model.Product

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductGridAffiliate(
    domain: String,
    products: List<Product>,
    onItemClick: (Product) -> Unit,
    onClickCart: (Product) -> Unit,
    onClickAddServiceRequest: (Product) -> Unit,

    modifier: Modifier = Modifier
) {
    val rows = products.chunked(2) // mỗi dòng 2 sản phẩm

    Column(modifier = modifier) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp),

            ) {
                rowItems.forEach { product ->
                    Box(modifier = Modifier.weight(1f)) {
                        ProductCardAffiliate(domain, product, onClick = { onItemClick(product) } , onClickCart = { onClickCart(product) } , onClickAddServiceRequest = { onClickAddServiceRequest(product) }  )
                    }
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
