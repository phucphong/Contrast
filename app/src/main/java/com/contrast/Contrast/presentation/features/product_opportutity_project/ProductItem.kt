package com.contrast.Contrast.presentation.features.product_opportutity_project


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
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.extensions.formatDouble
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.FFFF5722
import com.itechpro.domain.model.product.Product
import com.itechpro.domain.model.product.ProductOpoortutityProject

@Composable
fun ProductItem(
    domain: String,
    obj: ProductOpoortutityProject,
    onClickItem: (String) -> Unit = {},
    modifier: Modifier = Modifier


) {

    val fullUrl = "${domain}${obj.avata}"




    Column (
        modifier = modifier
            .fillMaxWidth()
            .noRippleClickableComposable { onClickItem(obj.id ?: "") },

    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(vertical = 8.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Hình ảnh sản phẩm
            NetworkImage(
                model = fullUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Nội dung bên phải
            Column(
                modifier = Modifier.weight(1f)
            ) {
                CustomText(
                    text = " ${(obj.soluong?:0.0).formatDouble()} x (${obj.tendonvi}) ${obj.tensanpham} ",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    maxLines = 2,
                    modifier = Modifier.padding(vertical = 6.dp).wrapContentWidth()
                )

                Row (modifier = Modifier.padding(bottom = 5.dp)){
                    CustomText(
                        text = stringResource(R.string.price),
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.wrapContentWidth()

                    )
                    CustomText(
                        text =( obj.dongia?:0.0).formatCurrency(),
                        color = Color.Black,
                        textAlign = TextAlign.Right,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row (modifier = Modifier.padding(bottom = 5.dp)){
                    CustomText(
                        text = stringResource(R.string.vat),
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.wrapContentWidth()
                        )
                    CustomText(
                        text ="${( obj.phantramthue?:0.0).formatDouble()}%",
                        color = Color.Black,
                        textAlign = TextAlign.Right,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row (modifier = Modifier.padding(bottom = 5.dp)){
                    CustomText(
                        text = stringResource(R.string.final_amount),
                        color = Color.Black,
                        fontSize = 14.sp,
                        modifier = Modifier.wrapContentWidth()
                        )
                    CustomText(
                        text =( obj.thanhtien?:0.0).formatCurrency(),
                        color = Color.Black,
                        textAlign = TextAlign.Right,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f)
                    )
                }


            }


        }
        CustomDividerColor()
    }
}
