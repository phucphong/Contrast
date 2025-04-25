package com.contrast.Contrast.presentation.features.product.ui


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*



import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale


import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.image.NetworkImage


import com.contrast.Contrast.presentation.features.affiliate.home.ProductPriceSection
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import kotlinx.coroutines.flow.StateFlow

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductCardAffiliate(
    modifier: Modifier = Modifier, // 👈 nhận modifier từ bên ngoài
    domain: String,
    product: Product,
    promoUiDataFlow: StateFlow<PromoUiData>?, // 🔁 truyền flow riêng
    onClick: () -> Unit,
    onClickCart: () -> Unit,
    onClickAddServiceRequest: () -> Unit
) {
    val totalPrice = product.sotien ?: 0.0
    val promoPrice = product.sotiensaukm ?: 0.0
    val fullUrl = remember(product.filetxt) {
        domain.trimEnd('/') + product.filetxt.orEmpty()
    }

    val promoUiData by promoUiDataFlow?.collectAsState() ?: remember { mutableStateOf(PromoUiData()) }


    Column(
        modifier = modifier
    ) {


        NetworkImage(
            model = fullUrl,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)

        )

        Text(
            text = product.ten.orEmpty(),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .padding(top = 6.dp),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(6.dp))

        ProductPriceSection(
            promoUiData = promoUiData,
            price = totalPrice,
            promoPrice = promoPrice,
            onClickCart = onClickCart,
            onClickAddServiceRequest = onClickAddServiceRequest
        )


        Spacer(Modifier.height(5.dp))
    }
}
