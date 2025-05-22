package com.contrast.Contrast.presentation.features.order.list.item

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
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.FFFF5722
import com.itechpro.domain.model.product.Product


@Composable
fun OrderItemView(
    status: String?,
    type: String?,
    fullUrl: String,
    orderKey: String,
    totalAmount: String,
    dateOrder: String,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(5.dp).noRippleClickableComposable { onItemClick() }
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(4.dp))
    ) {
        // Trạng thái đơn hàng
        if (!status.isNullOrEmpty() &&type!= "donhang") {

            Text(
                text = status ?: "",
                color = Color.Black,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f) .padding(5.dp)
            )

        }

        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(5.dp),
        ) {
            // Hình ảnh danh mục
            Card(
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .size(70.dp
                    )
            ) {

                NetworkImage(
                    model = fullUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .width(67.dp)
                        .height(67.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))

                )

            }

            // Nội dung bên phải
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = orderKey,
                    color = Color.Black,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = (totalAmount.toDouble()).formatCurrency(),
                    color = FFFF5722,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = dateOrder,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                )
            }
        }
    }
}
