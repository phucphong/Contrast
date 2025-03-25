package com.contrast.Contrast.presentation.features.membership.rewards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDivider
import com.contrast.Contrast.presentation.theme.FF151515

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun ContrastRewardsScreen() {
    val menuItems = listOf(
        "Quyền lợi hạng thành viên",
        "Chính sách thành viên",
        "Voucher của bạn",
        "Đổi cup lấy voucher",
        "Lịch sử đổi Cup"
    )

    Box(modifier = Modifier.fillMaxSize()) {


          Box() {  Image(
              painter = painterResource(R.drawable.rewards_1),
              contentDescription = null,
              modifier = Modifier
                  .fillMaxWidth()
                  .height(170.dp)
                  .scale(1.9f, 1f)
                  .align(Alignment.TopCenter)
          )

              // 🏷 2. Title đặt layer phía trên ảnh
              Text(
                  text = "CONTRAST\nREWARDS",
                  color = Color.White,
                  fontWeight = FontWeight.Bold,
                  fontSize = 20.sp,
                  textAlign = TextAlign.Center,
                  modifier = Modifier
                      .padding(top = 56.dp)
                      .align(Alignment.TopCenter)
              )
          }

        // 🧾 3. Nội dung scrollable, chừa khoảng trống đúng bằng ảnh header + title

         LazyColumn(
             modifier = Modifier
                 .fillMaxSize()
                 .padding(0.dp,110.dp,0.dp,30.dp) // Chừa đủ chiều cao header (200dp ảnh + khoảng top text)
                 .background(Color.Transparent),

             ) {
             // 🧭 Card cấp bậc
             item {
                 Image(
                     painter = painterResource(R.drawable.rewards),
                     contentDescription = "Illustration",
                     modifier = Modifier
                         .height(230.dp)
                         .fillMaxWidth().padding( vertical = 6.dp, horizontal = 15.dp),

                     contentScale = ContentScale.FillBounds
                 )
                 Spacer(modifier = Modifier.height(30.dp))
             }

             // 🏆 Card điểm số
             item {
                 Card(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding( vertical = 6.dp, horizontal = 30.dp),
                     shape = RoundedCornerShape(16.dp),
                     elevation = CardDefaults.cardElevation(6.dp)
                 ) {
                     Column(modifier = Modifier.padding(16.dp)) {
                         Row(verticalAlignment = Alignment.CenterVertically) {
                             Text(
                                 text = "40",
                                 style = TextStyle(
                                     fontSize = 20.sp,
                                     fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                                     fontWeight = FontWeight.SemiBold,
                                     color = FF151515,
                                 )
                             )

                             Image(
                                 painter = painterResource(R.drawable.cup_1),
                                 contentDescription = "iconmenu",
                                 modifier = Modifier
                                     .size(30.dp)
                                     .padding(start = 5.dp)
                             )

                             Spacer(modifier = Modifier.weight(1f))

                             Button(
                                 onClick = { /* TODO */ },
                                 shape = RoundedCornerShape(20.dp),
                                 contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                             ) {
                                 Text("Đổi voucher")
                             }
                         }

                         Spacer(modifier = Modifier.height(12.dp))

                         LinearProgressIndicator(
                             progress = 0.4f,
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .height(6.dp)
                                 .clip(RoundedCornerShape(4.dp)),
                             color = Color(0xFFE40000)
                         )

                         Spacer(modifier = Modifier.height(8.dp))

                         Text(
                             text = "Trailblazer             Achiever",
                             fontSize = 13.sp,
                             color = Color.Gray
                         )

                         Spacer(modifier = Modifier.height(10.dp))

                         Text(
                             text = "• Cần phát sinh tối thiểu 01 đơn hàng trong 03 tháng để giữ hạng\n• 1 cup = 10.000 VND",
                             fontSize = 13.sp,
                             color = Color(0xFF151515)
                         )
                     }
                 }
             }

             // 📋 Menu danh sách
             items(menuItems) { item ->
                 Column(
                     modifier = Modifier
                         .fillMaxWidth()
                         .clickable { /* TODO: Navigate */ }
                 ) {
                     Row(
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding( vertical = 12.dp, horizontal = 25.dp),
                         verticalAlignment = Alignment.CenterVertically
                     ) {
                         Image(
                             painter = painterResource(R.drawable.cup_1),
                             contentDescription = "iconmenu",
                             modifier = Modifier
                                 .size(30.dp)
                                 .padding(5.dp)
                         )

                         Text(
                             text = item,
                             style = TextStyle(
                                 fontSize = 14.sp,
                                 lineHeight = 16.8.sp,
                                 fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
                                 fontWeight = FontWeight.Normal,
                                 color = Color(0xFF151515),
                             ),
                             modifier = Modifier
                                 .padding(horizontal = 10.dp)
                                 .weight(1f)
                         )

                         Icon(
                             imageVector = Icons.Default.KeyboardArrowRight,
                             contentDescription = null
                         )
                     }
                     CustomDivider()
                 }
             }
         }
     }

}


