package com.contrast.Contrast.presentation.features.product.detail.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.presentation.components.progressBar.PromoProgressBar
import com.contrast.Contrast.presentation.theme.FFFF9800
import com.contrast.Contrast.presentation.theme.FFFF9800

@Preview(showBackground = true)
@Composable
fun ProductPriceDetailSection(
    productName: String,
    priceDisCount: Double = 0.0,
    price: Double = 0.0,
    discountPercent: Double = 0.0,
    isFlashSale: Boolean = false,
    remainingTime: String = "",
    quantity: Double,
    increaseQuantity: () -> Unit,
    showQuantity: () -> Unit,
    decreaseQuantity: () -> Unit,
) {

    Log.e("quantity",quantity.toString())
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp)
    ) {
        // Tên sản phẩm
        Text(
            text = productName,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )



        // Giá và giảm giá
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {

            if (priceDisCount!=0.0 && discountPercent!=0.0){
                Image(painter = painterResource(R.drawable.flash_sale),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(FFFF9800),
                    modifier = Modifier.size(30.dp).padding(5.dp)
                )
            }


            Text(
                text = "đ",
                color =if(isFlashSale) FFFF9800 else Color.Black,
                fontSize = 13.sp
            )
            Text(
                text = if(isFlashSale)priceDisCount.formatDouble()else price.formatDouble(),
                color =if(isFlashSale) FFFF9800 else Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            if (priceDisCount!=0.0 && discountPercent!=0.0) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "đ",
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray,
                    fontSize = 14.sp, modifier = Modifier.padding( top = 4.dp)
                )
                Text(
                    text = price.formatDouble(),
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Row (
                    modifier = Modifier
                        .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(4.dp))
                        .border(2.dp, FFFF9800,
                            RoundedCornerShape(4.dp)
                        )
                ) {
                    Text(text = discountPercent.formatDouble(), fontSize = 12.sp, color = Color.Gray
                        , modifier = Modifier.padding( 4.dp,4.dp,2.dp,4.dp))
                    Text( text = "%",
                        textDecoration = TextDecoration.LineThrough, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(0.dp,4.dp,4.dp,4.dp))
                }
            }
        }

        // Flash sale nếu có
        if (isFlashSale) {
        Column (modifier = Modifier  .background( if(isFlashSale) FFFF9800 else Color.White, shape = RoundedCornerShape(4.dp))){
                Box(
                    modifier = Modifier
                        .fillMaxWidth().padding(8.dp)
                        .background(FFFF9800, shape = RoundedCornerShape(6.dp))

                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.flash_sale),
                            fontStyle = FontStyle.Italic,
                            color = Color.White,
                            fontSize = 12.sp,
                        )
                        Box (modifier = Modifier.width(8.dp).height(12.dp).padding(horizontal = 4.dp).background(Color.White))
                        PromoProgressBar()
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Kết thúc sau $remainingTime",
                            color = Color.White,
                            fontSize = 11.sp
                        )
                    }
                }

           Box(modifier = Modifier.padding(
                2.dp
           )){
               QuantitySelector(quantity = quantity,
                   decreaseQuantity = decreaseQuantity,
                   showQuantity = showQuantity,
                   increaseQuantity = increaseQuantity)
           }
           }

            // Chọn số lượng

        }else{
            QuantitySelector(quantity = quantity,   decreaseQuantity = decreaseQuantity,
                showQuantity = showQuantity,
                increaseQuantity = increaseQuantity)
        }
    }
}
