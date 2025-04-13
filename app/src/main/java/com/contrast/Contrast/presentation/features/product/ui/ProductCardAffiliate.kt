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
import com.contrast.Contrast.presentation.components.progressBar.FlashSaleSeekBar
import com.contrast.Contrast.presentation.components.progressBar.PromoProgressBar
import com.itechpro.domain.model.Product
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ProductCardAffiliate(domain: String, product: Product) {
    val totalPrice = product.sotien ?: 0.0
    val promoPrice = product.sotiensaukm?:0.0
    val startDate = product.tungay?:""
    val endDate =  product.denngay?:""
    val fullUrl = domain.trimEnd('/') + product.filetxt.orEmpty()

    val isPromo = startDate.isNotEmpty() && endDate.isNotEmpty()
    var remainingTime by remember { mutableStateOf("") }

    if (isPromo) {
        rememberCountdownTimer(endDate) { time ->
            remainingTime = time
        }
    }

    Column(
        modifier = Modifier
            .width(180.dp)

            .background(Color.White)
            .border(1.dp, Color(0xFFE0E0E0))
            .padding(2.dp,2.dp,2.dp,0.dp)
    ) {
        AsyncImage(
            model = fullUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(6.dp))
        )

        Text(
            text = product.ten ?: "",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 6.dp, start = 4.dp, end = 4.dp),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(6.dp))

        Column {
            if (isPromo) {


                Box(

                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
                ) {


                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                    val start = LocalDateTime.parse(startDate, formatter)
                    val end = LocalDateTime.parse(endDate, formatter)
                    val totalDuration = Duration.between(start, end).toMillis().toFloat()

                    val current = LocalDateTime.now()
                    val elapsedDuration = Duration.between(start, current).toMillis().coerceAtLeast(0).toFloat()
                    val progress = (elapsedDuration / totalDuration).coerceIn(0f, 1f)
                    FlashSaleSeekBar(progress = progress, remainingTime = remainingTime)


                }
            }

            if(isPromo){
                PromoPriceBar(price = promoPrice.formatCurrency())

            }else{
                PriceBar(price = totalPrice.formatCurrency())
            }

            Box(Modifier.size(5.dp))

        }

    }
}
