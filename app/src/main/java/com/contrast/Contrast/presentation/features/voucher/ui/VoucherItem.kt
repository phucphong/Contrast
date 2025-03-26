package com.contrast.Contrast.presentation.features.voucher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.FFD91E18
import com.itechpro.domain.model.Voucher

@Composable
fun VoucherItem(voucher: Voucher, isVisible :Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() // Đảm bảo Box không bị ép chiều cao
            .padding(vertical = 10.dp, horizontal = 5.dp)
    ) {
        // Background Image (co giãn theo Column)
        Image(
            painter = painterResource(R.drawable.vourcher_special),
            contentDescription = null,
            modifier = Modifier
                .matchParentSize(), // Ảnh sẽ tự động mở rộng theo Column
            contentScale = ContentScale.FillBounds // Crop ảnh để giữ tỷ lệ tự nhiên
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() // Đảm bảo chiều cao tự động theo nội dung
                .padding(40.dp, 12.dp, 12.dp, 10.dp)
        ) {
            Text(
                text = voucher.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = " ${voucher.expiryDate}",
                fontSize = 12.sp,
                color = Color.Gray
            )


            // Box chứa nút "Áp dụng"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp, 5.dp, 1.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                if(isVisible){
                    Row(verticalAlignment = Alignment.Bottom) {

                        Image(
                            painter = painterResource(R.drawable.cup_1),
                            contentDescription = "cup icon",
                            modifier = Modifier.size(25.dp)
                        )

                        Text(
                            text = "100",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = FF151515,
                            modifier = Modifier.wrapContentWidth().padding(start = 5.dp,end = 10.dp)
                        )
                    }


                }else{
                    Box(
                        Modifier
                            .width(59.dp)
                            .height(15.dp)
                            .background(color = FFD91E18, shape = RoundedCornerShape(size = 31.dp)),
                        contentAlignment = Alignment.Center,


                    ) {
                        Text(
                            text = "Áp dụng",
                            style = TextStyle(
                                fontSize = 9.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(500),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                }



            }
        }
    }
}