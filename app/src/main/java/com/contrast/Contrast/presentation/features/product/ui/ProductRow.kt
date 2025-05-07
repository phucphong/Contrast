package com.contrast.Contrast.presentation.features.product.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.PromoUiData
import kotlinx.coroutines.flow.StateFlow
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductRow(
    domain: String,
    token: String,
    pointAffiliate: String,
    isShare: Boolean=false,
    rowProducts: List<Product>,
    promoUiDataMap: Map<String, StateFlow<PromoUiData>>,
    onItemClick: (Product) -> Unit,
    onClickCart: (Product) -> Unit,
    onClickAddServiceRequest: (Product) -> Unit,
    onClickShare: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        rowProducts.forEach { product ->
            ProductCardAffiliate(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.White)
                    .noRippleClickableComposable { onItemClick(product) }
                    .padding(2.dp),
                domain = domain,
                token = token,
                isShare = isShare,
                pointAffiliate = pointAffiliate,
                product = product,
                promoUiDataFlow = promoUiDataMap[product.id],
                onClick = { onItemClick(product) },
                onClickCart = { onClickCart(product) },
                onClickAddServiceRequest = { onClickAddServiceRequest(product) },
                onClickShare = { onClickShare(product) },
            )
        }

        // Nếu dòng chỉ có 1 item, thêm spacer để căn giữa
        if (rowProducts.size < 2) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
