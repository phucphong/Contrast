package com.contrast.Contrast.presentation.components.searchBar



import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.cart.CartIconWithBadge
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.notification.NotificationIconWithBadge
import com.contrast.Contrast.presentation.theme.TealGreen

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun TopTextNotificationShare(
    painter: Painter = painterResource(R.drawable.back),
    modifier: Modifier = Modifier,
    placeholder: String = "",
    totalCartItems: Int = 0,
    isBackStack: Boolean = true,
    text: String = "",
    onShareClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    onBackStack: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if(isBackStack){
            Image(
                painter= painter,
                contentDescription = "Search Icon",
                modifier = Modifier.size(35.dp).padding(5.dp).noRippleClickableComposable { onBackStack() }
                , colorFilter = ColorFilter.tint(TealGreen)
            )
        }


        Text(
            text = placeholder,
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
               ,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(500)


        )





        // Cart Icon
        CartIconWithBadge(
            count = totalCartItems,
            onCartClick = {
                // Xử lý click giỏ hàng
                onCartClick()
            }
        )

        Image(
            painter = painterResource(id = R.drawable.share),
            contentDescription = "Cart",
            modifier = Modifier
                .noRippleClickableComposable { onShareClick()}
                .size(30.dp).padding(5.dp),
            colorFilter = ColorFilter.tint(TealGreen)
        )

    }
}
