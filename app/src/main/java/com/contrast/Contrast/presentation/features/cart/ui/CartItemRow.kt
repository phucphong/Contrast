package com.contrast.Contrast.presentation.features.cart.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.presentation.components.checkbox.CheckBoxColor
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.theme.FF9E9E9E
import com.contrast.Contrast.presentation.theme.FFAFAFAF
import com.contrast.Contrast.presentation.theme.FFE0E0E0
import com.contrast.Contrast.presentation.theme.FFFF9800
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.theme.UltraLightGray

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
    val moneyDisCount = cart.sotienkm ?: 0.0
    var  price = 0.0
    val  count = cart.soluong?:0.0
    if(moneyDisCount>0){
        price = cart.sotiensaukm?:0.0
    }else{

        price = cart.dongia?:0.0
    }

    val  productName = cart.tensanpham?:""
    val  isChecked = cart.isChecked?:false



    val fullUrl = remember(cart.filetxt) {
        domain.trimEnd('/') + cart.filetxt.orEmpty()
    }

  Box (contentAlignment = Alignment.TopEnd){   Row(
      modifier = Modifier
          .fillMaxWidth()
          .wrapContentHeight()
          .clip(RoundedCornerShape(8.dp))
          .background(Color.White)
          .border(1.dp,if(moneyDisCount>0) FFFF9800 else FFE0E0E0, shape = RoundedCornerShape(8.dp))
          .padding(15.dp),
      verticalAlignment = Alignment.CenterVertically
  ){
      CheckBoxColor(checked = isChecked,padding=2.dp, size=15.dp,       backgroundChecked = TealGreen, backgroundUnChecked = TealGreen,onCheckedChange = onCheckedChange)

      Spacer(modifier = Modifier.width(8.dp))


      NetworkImage(
          model = fullUrl,
          contentDescription = null,
          contentScale = ContentScale.Fit,
          modifier = Modifier
              .width(67.dp)
              .height(67.dp)
              .background(Color.LightGray, shape = RoundedCornerShape(8.dp))

      )

      Spacer(modifier = Modifier.width(8.dp))

      Column(
          modifier = Modifier.weight(1f).padding(horizontal = 5.dp), verticalArrangement = Arrangement.Center

      ) {
          Text(text = productName, fontWeight =FontWeight.Normal, color = FF9E9E9E, fontSize = 11.sp, modifier = Modifier.padding(top = 15.dp), maxLines = 1)
          Spacer(modifier = Modifier.height(4.dp))

          Row {
              Text(text = price.formatDouble(), fontWeight = FontWeight.Normal, fontSize = 12.sp, modifier = Modifier.weight(1f))


              QuantitySelectorCart(

                  quantity = count,
                  increaseQuantity = increaseQuantity,
                  onQuantityChange = onQuantityChange,
                  decreaseQuantity = decreaseQuantity,
                  modifier =Modifier.clip(RoundedCornerShape(4.dp))
                      .border(1.dp, FF9E9E9E, RoundedCornerShape(4.dp))
                      .background(Color.White)
              )}
      }


  }
      if(moneyDisCount>0){

          Box( modifier = Modifier
              .wrapContentWidth()
              .wrapContentHeight()
              .clip(RoundedCornerShape(0.dp,6.dp,0.dp,6.dp))
              .background(FFFF9800)

              .padding(6.dp),){
              Text(text = stringResource(R.string.flash_sale),  fontStyle = FontStyle.Italic,fontWeight =FontWeight.Normal, color = Color.White, fontSize = 10.sp, maxLines = 1)


          }
      }

  }
}
