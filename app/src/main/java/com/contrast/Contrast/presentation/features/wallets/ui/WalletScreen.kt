package com.contrast.Contrast.presentation.features.wallets.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.core.GrayD7
import com.contrast.Contrast.presentation.components.text.CustomText

@Preview(showBackground = true)
@Composable
fun WalletScreenPreview() {
    WalletScreen(onBackPress = {}, onTopUp = {}, onPay = {})
}

@Composable
fun WalletScreen(
    onBackPress: () -> Unit,
    onTopUp: () -> Unit,
    onPay: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD32F2F)),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(16.dp))
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()

                .padding(16.dp)
               // Bo tròn góc trên
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBackPress) {
                    Image(
                        painter = painterResource(R.drawable.back),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White), // Áp dụng tint màu trắng
                        contentDescription = "",
                        modifier = Modifier.width(30.dp).height(30.dp)

                    )

                }
                Text(
                    text = stringResource(id = R.string.wallet_title),
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { /* Handle cart click */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.receipt),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp).
                        padding(5.dp)

                    )
            }
            }
        }



        // Balance Section
       Column( modifier = Modifier
           .fillMaxWidth()
           .fillMaxHeight()
           .background(Color.White, shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
           .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)) // Bo tròn góc trên


       ) {
           Column(
               modifier = Modifier
                   .fillMaxWidth(0.8f)
                   .background(Color.White)
                   .padding(16.dp)

           ) {
               Spacer(modifier = Modifier.height(16.dp))
               Text(text = stringResource(id = R.string.balance_label), fontSize = 16.sp, color = Color.Gray)
               Spacer(modifier = Modifier.height(4.dp))
               Row(verticalAlignment = Alignment.CenterVertically) {
                   Text(text = "₫ 550,656", fontSize = 24.sp, color = Color.Black)
                   Spacer(modifier = Modifier.width(8.dp))
                   IconButton(onClick = { /* Refresh balance */ }) {
                       Icon(
                           painter = painterResource(id = R.drawable.refresh),
                           contentDescription = stringResource(R.string.refresh),
                           tint = Color.Black
                       )
                   }
               }
           }

           Spacer(modifier = Modifier.height(16.dp))

           // Buttons
           Row(
               modifier = Modifier
                   .fillMaxWidth(1f).padding(16.dp),

               horizontalArrangement = Arrangement.spacedBy(12.dp) // Giữ khoảng cách giữa hai nút
           ) {
               Button(
                   onClick = onTopUp,
                   colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                   shape = RoundedCornerShape(8.dp),
                   modifier = Modifier
                       .weight(1f)
                       .height(48.dp) // Đảm bảo chiều cao thống nhất
                       .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)) // Viền đen
               ) {

                   Image(painter = painterResource(R.drawable.plus),
                       contentDescription = "",
                       modifier = Modifier.width(20.dp).height(20.dp)
                   )
                   Spacer(modifier = Modifier.width(4.dp))
                   CustomText(text = stringResource(id = R.string.top_up), color = Color.Black)
               }

               Button(
                   onClick = {

                   },
                   colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                   shape = RoundedCornerShape(8.dp),
                   modifier = Modifier
                       .weight(1f)
                       .height(48.dp) // Đảm bảo chiều cao thống nhất
               ) {

                   Image(painter = painterResource(R.drawable.moneysend),
                       contentDescription = "",
                       modifier = Modifier.width(20.dp).height(20.dp)
                   )



                   Spacer(modifier = Modifier.width(4.dp))
                   CustomText(text = stringResource(id = R.string.pay), color = Color.White)
               }

               Spacer(modifier = Modifier.width(10.dp))
           }


           Spacer(modifier = Modifier.height(30.dp))

           // Card Section
           Image(
               painter = painterResource(id = R.drawable.rewards),
               contentDescription = stringResource(R.string.reward_card),
               modifier = Modifier
                   .fillMaxWidth()
                   .height(230.dp)
           )



           // Footer Message
           Column(horizontalAlignment = Alignment.CenterHorizontally) {
               Image(
                   painter = painterResource(id = R.drawable.money),
                   contentDescription = stringResource(R.string.voucher_icon),
                   modifier = Modifier.size(50.dp)
               )
               Spacer(modifier = Modifier.height(8.dp))


               CustomText(
                   text = stringResource(id = R.string.quick_payment_message),
                   fontWeight = FontWeight.Normal,
                   fontSize = 16.sp, GrayD7 ,
                   TextAlign.Center,


               )
           }
       }
    }
}
