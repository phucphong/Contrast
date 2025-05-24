package com.contrast.Contrast.presentation.features.order.detail.item


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.theme.FFFF5722
@Composable
fun OrderItemViewDetail(
    fullUrl: String,
    orderKey: String,
    totalAmount: String,
    quantity: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
          ,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Hình ảnh sản phẩm
            NetworkImage(
                model = fullUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Nội dung bên phải
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = orderKey,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 5.dp)
                )



                Text(
                    text = totalAmount,
                    color = Color.Black,
                    fontSize = 14.sp
                    ,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }

            // Số lượng canh phải
            Text(
                text = "x$quantity",
                color = Color.Black,
                fontSize = 13.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(start = 8.dp, top = 10.dp)
            )
        }
    }
}
