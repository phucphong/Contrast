package com.contrast.Contrast.presentation.features.product.ui


import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.itechpro.domain.model.Product


@Preview(showBackground = true)
@Composable
fun ProductCardAffiliate(domain: String, product: Product) {
    val totalPrice: Double = product.sotien ?: 0.0
    val fullUrl = domain.trimEnd('/') + product.filetxt.orEmpty()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(4.dp)
    ) {
        AsyncImage(
            model = fullUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = product.ten ?: "",
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 6.dp, start = 4.dp, end = 4.dp),
            maxLines = 2
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = totalPrice.formatCurrency(),
                color = Color(0xFF00BFA6),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Icon(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
