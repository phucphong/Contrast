package com.contrast.Contrast.presentation.features.main.store.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.layout.*


import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.*
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FF404040


@Composable
fun ProductCard(
    title: String,
    price: String,
    imageRes: Int,
    discount: String? = null,
    onAdd: () -> Unit
) {


       Box(

   ) {
       Column(
           modifier = Modifier
               .width(160.dp)
               .height(232.dp)
               .clip(RoundedCornerShape(12.dp)).padding(start = 5.dp)
               .background(Color.White)
               .border(1.dp, Color(0xFFCFCFCF), shape = RoundedCornerShape(12.dp))
       ) {
           Box {
               Image(
                   painter = painterResource(id = imageRes),
                   contentDescription = null,
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(140.dp)
                       .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                   contentScale = ContentScale.Crop
               )


           }

           Spacer(modifier = Modifier.height(8.dp))

           Column(modifier = Modifier.padding(horizontal = 8.dp)) {
               Text(
                   text = title,
                   fontSize = 14.sp,
                   fontWeight = FontWeight.Medium,
                   color = Color.Black
               )

               Spacer(modifier = Modifier.height(4.dp))

               Text(
                   text = price,
                   style = TextStyle(
                       fontSize = 14.sp,
                       lineHeight = 21.sp,
                       fontFamily = FontFamily(Font(R.font.inter)),
                       fontWeight = FontWeight(500),
                       color = Color(0xFF151515),

                       )
               )

             Row(){
                 Box (Modifier.weight(1f))

                     Image(
                     painter = painterResource(id = R.drawable.plus_square),
                     contentDescription = "image description",
                     contentScale = ContentScale.None
                     , modifier = Modifier.size(25.dp).border(1.dp, FF404040,
                             shape = RoundedCornerShape(5.dp))
                         .clip(RoundedCornerShape(5.dp)) // Bắt buộc để bo ảnh theo border


                 )

             }
           }
       }


   }
       // Ribbon giảm giá
       if (discount != null) {
           DiscountRibbon(discount)
       }

}

@Composable
fun DiscountRibbon(
    text: String,

    ) {
    Box(
        modifier = Modifier.padding(top = 12.dp)

    ) {

        Box () {
            Image(
                painter = painterResource(R.drawable.discount_bottom_left),
                contentDescription = "rectangle_discount",
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth().padding(top = 27.dp)
                ,
            )
            Image(
                painter = painterResource(R.drawable.rectangle_discount),
                contentDescription = "rectangle_discount",
                modifier = Modifier
                    .height(28.dp)
                    .wrapContentWidth()
            )


        }

        Text(
            text = text,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 26.61.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.fillMaxHeight().padding(top = 6.dp, start = 8.dp)
        )

    }
}