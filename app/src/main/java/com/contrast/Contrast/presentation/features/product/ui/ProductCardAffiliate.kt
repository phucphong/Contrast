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
import com.itechpro.domain.model.Product
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductCardAffiliate(
    domain: String,
    product: Product,
    onClick: () -> Unit,
    onClickCart: () -> Unit,
    onClickAddServiceRequest: () -> Unit
) {
    val totalPrice = product.sotien ?: 0.0
    val promoPrice = product.sotiensaukm ?: 0.0
    val startDate = product.tungay ?: ""
    val endDate = product.denngay ?: ""
    val fullUrl = domain.trimEnd('/') + product.filetxt.orEmpty()

    val isPromo = startDate.isNotEmpty() && endDate.isNotEmpty()

    // Parse date chỉ 1 lần
    val formatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm") }
    val startTime = remember(startDate) {
        runCatching { LocalDateTime.parse(startDate, formatter) }.getOrNull()
    }
    val endTime = remember(endDate) {
        runCatching { LocalDateTime.parse(endDate, formatter) }.getOrNull()
    }

    // Countdown & progress riêng biệt
    var remainingTime by remember { mutableStateOf("00:00:00") }

    val progress by remember {
        derivedStateOf {
            if (startTime != null && endTime != null) {
                val total = Duration.between(startTime, endTime).toMillis().toFloat()
                val now = LocalDateTime.now()
                val elapsed = Duration.between(startTime, now).toMillis().coerceAtLeast(0)
                (elapsed / total).coerceIn(0f, 1f)
            } else 0f
        }
    }

    LaunchedEffect(endTime) {
        if (isPromo && endTime != null) {
            while (true) {
                val now = LocalDateTime.now()
                val remain = Duration.between(now, endTime).coerceAtLeast(Duration.ZERO)
                val h = remain.toHours()
                val m = remain.toMinutes() % 60
                val s = remain.seconds % 60
                remainingTime = String.format("%02d:%02d:%02d", h, m, s)
                delay(1000)
            }
        }
    }

    // UI chính
    Column(
        modifier = Modifier
            .width(180.dp)
            .background(Color.White)
            .noRippleClickableComposable { onClick() }
            .padding(2.dp, 2.dp, 2.dp, 0.dp)
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
            text = product.ten ?: "",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 6.dp, start = 4.dp, end = 4.dp),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(6.dp))

        if (isPromo) {
            Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)) {
                FlashSaleSeekBar(progress = progress, remainingTime = remainingTime)
            }

            PromoPriceBar(price = promoPrice.formatCurrency())
        } else {
            PriceBar(
                price = totalPrice.formatCurrency(),
                onClickCart = onClickCart,
                onClickAddServiceRequest = onClickAddServiceRequest,
            )
        }

        Spacer(Modifier.size(5.dp))
    }
}
