package com.contrast.Contrast.presentation.features.cart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.checkbox.CheckBoxColor
import com.contrast.Contrast.presentation.components.media.NetworkImage

import com.itechpro.domain.model.cart.CartItem

@Composable
fun CartItemRow(
    cart: CartItem,
    domain: String,

    onCheckedChange: (Boolean) -> Unit = {},
    increaseQuantity: () -> Unit = {},
    onQuantityChange: (Double) -> Unit = {},
    decreaseQuantity: () -> Unit = {},
) {

    val  price = cart.dongia?:0.0
    val  count = cart.soluong?:0.0
    val  productName = cart.tensanpham?:""
    val  isChecked = cart.isChecked?:false

    val fullUrl = remember(cart.filetxt) {
        domain.trimEnd('/') + cart.filetxt.orEmpty()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(vertical = 10.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CheckBoxColor(checked = isChecked,padding=1.dp, size=18.dp, onCheckedChange = onCheckedChange)

        Spacer(modifier = Modifier.width(8.dp))


        NetworkImage(
            model = fullUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(72.dp)
                .height(72.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))

        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)

        ) {
            Text(text = productName, fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.padding(top = 5.dp), maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = price.formatCurrency(), fontWeight = FontWeight.Medium, fontSize = 12.sp)
           Row(Modifier.padding(10.dp)) { Box (Modifier.weight(1f))
               QuantitySelectorCart(

                   quantity = count,
                   increaseQuantity = increaseQuantity,
                   onQuantityChange = onQuantityChange,
                   decreaseQuantity = decreaseQuantity,
               )}
        }


    }
}
