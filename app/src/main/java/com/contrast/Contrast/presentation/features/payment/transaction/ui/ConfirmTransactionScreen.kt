package com.contrast.Contrast.presentation.features.payment.transaction.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.theme.FFD9D9D9

import com.contrast.Contrast.presentation.theme.FFD7D7D7

import com.contrast.Contrast.presentation.components.topAppBar.CustomTitleBack
import com.contrast.Contrast.presentation.theme.FFD91E18


@Preview(showBackground = true)
@Composable
fun ConfirmTransactionScreenPreview() {
    ConfirmTransactionScreen(onClose = {}, onConfirm = {})
}

@Composable
fun ConfirmTransactionScreen(
    onClose: () -> Unit, onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FFD9D9D9), // Nền xám nhẹ
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        CustomTitleBack(
            title = "Xác nhận giao dịch",
            Color.White,
            onBackPress = { /* Xử lý quay lại */ }
        )
        Spacer(modifier = Modifier.height(30.dp))

        // Thẻ thông tin giao dịch

       Box (contentAlignment = Alignment.BottomCenter,
           ){

           Box(
               contentAlignment = Alignment.TopCenter
           ) {

               Card(
                   shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                   modifier = Modifier
                       .fillMaxWidth(0.9f)
                       .padding(8.dp, 22.dp)
                       .clip(RoundedCornerShape(10.dp)), // Cắt viền dưới
                   elevation = 8.dp // giảm elevation để tránh shadow rõ
               ){
                   Column(

                   ) {
                       // Icon loading
                       Image(
                           painter = painterResource(id = R.drawable.clock),
                           contentDescription = "Loading",
                           modifier = Modifier.size(40.dp)
                       )

                       Spacer(modifier = Modifier.height(8.dp))

                       Text(
                           modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp),

                           text = "Xác nhận thanh toán", fontSize = 16.sp, color = Color.Gray
                       )

                       Spacer(modifier = Modifier.height(8.dp))

                       Text(
                           modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp),

                           text = "₫150,000",
                           fontSize = 22.sp,
                           fontWeight = FontWeight.Bold,
                           color = Color.Black
                       )

                       Spacer(modifier = Modifier.height(16.dp))

                       Box(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp)
                               .height(1.dp)
                               .background(FFD7D7D7, shape = RoundedCornerShape(50))
                       )
                       Spacer(modifier = Modifier.height(30.dp))
                       // Thông tin chi tiết giao dịch
                       TransactionDetailItem("Từ tài khoản", "********686868", "Lương Duy Long")
                       Spacer(modifier = Modifier.height(16.dp))
                       TransactionDetailItem("Thanh toán cho", "Contrast Kham Thien")
                       Spacer(modifier = Modifier.height(16.dp))
                       TransactionDetailItem("Số hóa đơn", "CKT123456789")
                       Spacer(modifier = Modifier.height(16.dp))
                       TransactionDetailItem("Voucher áp dụng", "Không có")

                       Spacer(modifier = Modifier.height(16.dp))

                       Divider()
                       Spacer(modifier = Modifier.height(16.dp))
                       // Tổng thanh toán
                       Row(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(vertical = 8.dp),
                           horizontalArrangement = Arrangement.SpaceBetween
                       ) {
                           Text(   modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp),
                               text = "Tổng thanh toán", fontSize = 16.sp, color = Color.Black)
                           Text(   modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp),

                               text = "₫150,000",
                               fontSize = 16.sp,
                               fontWeight = FontWeight.Bold,
                               color = FFD91E18
                           )
                       }

                       Spacer(modifier = Modifier.height(16.dp))

                       // Nút chọn voucher
                       Button(
                           onClick = { /* Xử lý chọn voucher */ },
                           colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                           shape = RoundedCornerShape(8.dp),
                           modifier = Modifier
                               .fillMaxWidth()
                               .height(48.dp)
                               .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 0.dp)
                               .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                       ) {
                           Icon(
                               painter = painterResource(id = R.drawable.ic_wallet),
                               contentDescription = "Voucher",

                               modifier = Modifier.size(20.dp)
                           )
                           Spacer(modifier = Modifier.width(8.dp))
                           Text(text = "Chọn Voucher", color = Color.Black)
                       }

                       Spacer(modifier = Modifier.height(30.dp))
                       Image(modifier = Modifier.fillMaxWidth().scale(1.15f),
                           painter = painterResource(R.drawable.footter_paymen_commit),
                           contentDescription = "")
                   }
               }

               Box(
                   modifier = Modifier
                       .size(45.dp) // Kích thước hình tròn
                       .shadow(elevation = 8.dp, shape = CircleShape) // Đổ bóng
                       .background(Color.White, shape = CircleShape), // Nền trắng
                   contentAlignment = Alignment.Center
               ) {

                   Box(
                       modifier = Modifier
                           .size(30.dp) // Kích thước hình tròn
                           .background(Color(0xFFFFA500), shape = CircleShape), // Màu cam & hình tròn
                       contentAlignment = Alignment.Center
                   ) {

                       Image(
                           painter = painterResource(R.drawable.clock),
                           contentDescription = "",
                           modifier = Modifier
                               .height(25.dp)
                               .width(25.dp),


                           )
                   }



               }
           }
       }





    Spacer(modifier = Modifier.height(16.dp))

    // Nút xác nhận
    Button(
        onClick = { onConfirm() },
        colors = ButtonDefaults.buttonColors(backgroundColor = FFD91E18),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(48.dp)
    ) {
        Text(text = "Xác nhận", color = Color.White, fontSize = 16.sp)
    }

    Spacer(modifier = Modifier.height(16.dp))
}
}

@Composable
fun TransactionDetailItem(label: String, value: String, subValue: String? = null) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 14.sp, color = Color.Gray)
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
        if (subValue != null) {
            Text(

                text = subValue,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 0.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
