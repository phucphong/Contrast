package com.contrast.Contrast.presentation.features.voucher.redeem



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.voucher.redeem.ui.PointsAndVoucherCard
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.FFDC143C
import com.contrast.Contrast.presentation.features.voucher.ui.VoucherItem
import com.contrast.Contrast.presentation.theme.AFFFFFF
import com.contrast.Contrast.presentation.theme.FF87B5FB
import com.contrast.Contrast.presentation.theme.FFA7D2C0
import com.contrast.Contrast.presentation.theme.FFD91E18
import com.contrast.Contrast.presentation.theme.FFF9CB8C
import com.contrast.Contrast.presentation.theme.FFFB8B87
import com.itechpro.domain.model.Voucher


// Sample list of vouchers
val vouchers = listOf(
    Voucher(
        "Giảm giá ₫100,000 cho hóa đơn thứ 3 trong tuần",
        "Hết hạn vào 31/01/2026",

        "Standard"
    ), Voucher(
        "Giảm ₫10,000 cho hóa đơn đầu tiên", "Hết hạn vào 31/01/2026",

        "Standard"
    ), Voucher(
        "Contrast tặng bạn - Giảm giá ₫100,000 cho hóa đơn tiếp theo",
        "Hết hạn vào 31/01/2026",

        "Special"
    )
)

@Preview(showBackground = true)
@Composable
fun VoucherRedeemScreen() {


    var levelVoucher by remember { mutableStateOf("3") }


       Column(
           modifier = Modifier
               .fillMaxSize()
               .background(FFD91E18)

       ) {

           Spacer(modifier = Modifier.height(40.dp))

           // Thanh tiêu đề với nút đóng
           Row(
               modifier = Modifier
                   .fillMaxWidth().padding(20.dp)
               ,
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
           ) {
               // Tiêu đề màn hình

               Text(
                   text = stringResource(R.string.voucher_change),
                   fontSize = 24.sp,
                   fontWeight = FontWeight.Bold,
                   fontFamily = FontFamily(Font(R.font.inter_18pt_bold)),
                   color = Color.White
               )
               Box(
                   modifier = Modifier
                       .padding(8.dp)
                       .size(36.dp)
                       .background(AFFFFFF, shape = RoundedCornerShape(8.dp)), // 🔥 Viền bo tròn
                   contentAlignment = Alignment.Center
               ) {
                   // Nút đóng
                   IconButton(
                       onClick = {}
                   ) {
                       Icon(
                           painter = painterResource(id = R.drawable.close_2),
                           contentDescription ="",
                           tint = Color.White
                       )
                   }
               }


           }


           Column( modifier = Modifier
               .fillMaxWidth()
               .fillMaxHeight()

               .background(Color.White, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
               .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)) // Bo tròn góc trên


           ) {

               Spacer(modifier = Modifier.height(30.dp))
               Column(modifier = Modifier
                   .fillMaxWidth().padding(8.dp)

               ) {


                   LazyColumn {

                       item {
                          Column {   if(levelVoucher=="1"){
                              /** quỹ điểm của bạn */
                              PointsAndVoucherCard(
                                  points = 140,
                                  title = stringResource(R.string.your_point_fund),
                                  cupIcon = R.drawable.cup_1,
                                  borderColor = FFFB8B87,
                                  voucherIcon = R.drawable.voucher_1
                              )

                          }else   if(levelVoucher=="2"){
                              /** quỹ điểm của bạn */
                              PointsAndVoucherCard(
                                  points = 140,
                                  title = stringResource(R.string.your_point_fund),
                                  cupIcon = R.drawable.cup_2,
                                  borderColor = FF87B5FB,
                                  voucherIcon = R.drawable.voucher_2
                              )

                          }
                          else   if(levelVoucher=="3"){
                              /** quỹ điểm của bạn */
                              PointsAndVoucherCard(
                                  points = 140,
                                  title = stringResource(R.string.your_point_fund),
                                  cupIcon = R.drawable.cup_3,
                                  borderColor = FFA7D2C0,
                                  voucherIcon = R.drawable.voucher_3
                              )

                          }else   if(levelVoucher=="3"){
                              /** quỹ điểm của bạn */
                              PointsAndVoucherCard(
                                  points = 140,
                                  title = stringResource(R.string.your_point_fund),
                                  cupIcon = R.drawable.cup_4,
                                  borderColor = FFF9CB8C,
                                  voucherIcon = R.drawable.voucher_4
                              )

                          }


                              Spacer(modifier = Modifier.height(30.dp))
                              /** 🔹 DANH SÁCH CỬA HÀNG */
                              Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {


                                  androidx.compose.material.Text(
                                      text = stringResource(R.string.voucher_list),
                                      style = TextStyle(
                                          fontSize = 14.sp,
                                          fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                                          fontWeight = FontWeight(500),
                                          color = FF151515,
                                          textAlign = TextAlign.Left,
                                          letterSpacing = 0.15.sp,
                                      )
                                  )
                                  ClickableText(
                                      text = AnnotatedString(stringResource(R.string.owned_voucher)),

                                      onClick = { /* TODO: Xem tất cả cửa hàng */ },
                                      style = TextStyle(color = FFD91E18, fontSize = 14.sp)
                                  )
                              }
                              Spacer(modifier = Modifier.height(16.dp)) }
                       }
                       items(vouchers) { voucher ->
                           VoucherItem(voucher,true)
                       }
                   } }
           } }

}



