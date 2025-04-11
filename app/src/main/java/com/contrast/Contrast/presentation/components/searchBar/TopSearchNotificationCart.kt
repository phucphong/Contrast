package com.contrast.Contrast.presentation.components.searchBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.TealGreen

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun TopSearchNotificationCart(
    modifier: Modifier = Modifier,
    placeholder: String = "Tìm kiếm",
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onCartClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Search Box
        Box(
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF5F5F5))
                .noRippleClickableComposable { onSearchClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter= painterResource(R.drawable.search),
                    contentDescription = "Search Icon",

                    modifier = Modifier.size(20.dp)
                    , colorFilter = ColorFilter.tint(TealGreen)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Notification Icon
        IconButton(onClick = onNotificationClick) {
            Image(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Notification",
                modifier = Modifier.size(30.dp),
                colorFilter = ColorFilter.tint(TealGreen)
            )
        }

        // Cart Icon
        IconButton(onClick = onCartClick) {
            Image(
                painter = painterResource(id = R.drawable.cart),
                contentDescription = "Cart",
                modifier = Modifier.size(30.dp),
                colorFilter = ColorFilter.tint(TealGreen)
            )
        }
    }
}
