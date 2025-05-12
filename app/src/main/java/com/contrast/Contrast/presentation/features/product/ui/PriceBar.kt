package com.contrast.Contrast.presentation.features.product.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.itechpro.domain.model.navigationEvent.ProductNavEvent


@Composable
fun PriceBar(
    price: String,
    isShare: Boolean = false,
    bookService: Boolean = false,
    onClickCart: () -> Unit,
    onClickAddServiceRequest: () -> Unit,
    onClickShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(Modifier.padding(horizontal = 5.dp).height(52.dp), verticalAlignment = Alignment.CenterVertically) {



        Text(
            text = price,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00BFA6),
            modifier = Modifier.weight(1f)
        )
        if(!isShare){
            Icon(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = "Cart",
                tint = Color(0xFF00BFA6),
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(20.dp).noRippleClickableComposable { onClickCart() }
            )
            Box(Modifier.size(10.dp))
            if(bookService){
                Icon(
                    painter = painterResource(id = R.drawable.calendar_service),
                    contentDescription = "Buy Package",
                    tint = Color(0xFF00BFA6),
                    modifier = Modifier.size(20.dp).noRippleClickableComposable { onClickAddServiceRequest() }
                )
            }

        }else{
            Icon(
                painter = painterResource(id = R.drawable.share),
                contentDescription = "share",
                tint = Color(0xFF00BFA6),
                modifier = Modifier.size(20.dp).noRippleClickableComposable { onClickShare() }
            )
        }



    }
}