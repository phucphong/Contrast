package com.contrast.Contrast.presentation.features.product.ui


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.extensions.DateUtils.today
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.countdownTimer.rememberCountdownTimer
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.progressBar.FlashSaleSeekBar
import com.contrast.Contrast.presentation.components.progressBar.PromoProgressBar
import com.contrast.Contrast.presentation.features.affiliate.home.ProductPriceSection
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.PromoUiData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductCardAffiliate(
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
        modifier = Modifier
            .width(180.dp)
            .background(Color.White)
            .noRippleClickableComposable { onClick() }
            .padding(2.dp)
    ) {
        AsyncImage(
            model = fullUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
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
