package com.contrast.Contrast.presentation.features.membership.rewards

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.theme.AEA1F27
import com.contrast.Contrast.presentation.theme.EEA1F27
import com.contrast.Contrast.presentation.theme.EFFFFFF
import com.contrast.Contrast.presentation.theme.FF000000
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.FFAFAFAF
import com.contrast.Contrast.presentation.theme.FFE40000

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun RewardsScreen( navController: NavController) {
    val menuItems = listOf(
        stringResource(R.string.membership_benefits)to R.drawable.ic_medal,
        stringResource(R.string.membership_policy)to R.drawable.membership_policy,
        stringResource(R.string.your_voucher)to R.drawable.your_voucher,
        stringResource(R.string.exchange_cup_voucher)to R.drawable.ic_medal,
        stringResource(R.string.cup_exchange_history)to R.drawable.cup_exchange_history,
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
                  text = stringResource(R.string.contrast_rewards),
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
                         .fillMaxWidth().padding( vertical = 6.dp, horizontal = 8.dp),

                     contentScale = ContentScale.FillBounds
                 )
                 Spacer(modifier = Modifier.height(10.dp))
             }

             // 🏆 Card điểm số
             item {
                 Box(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding( vertical = 6.dp, horizontal = 23.dp)
                         .shadow(elevation = 12.dp, spotColor = Color(0x1AEA1F27), ambientColor = AEA1F27)
                         .border(width = 1.dp, color = Color(0xFFAFAFAF), shape = RoundedCornerShape(size = 8.dp))
                 ,

                 ) {
                     Column(modifier = Modifier.padding(16.dp)) {
                         Row(verticalAlignment = Alignment.CenterVertically) {
                             Text(
                                 text = "40",
                                 style = TextStyle(
                                     fontSize = 20.sp,
                                     fontFamily = FontFamily(Font(R.font.inter)),
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
                                 shape = RoundedCornerShape(50), // bo tròn như hình
                                 colors = ButtonDefaults.buttonColors(
                                     containerColor = Color.Black,
                                     contentColor = Color.White
                                 ),
                                 contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                                 elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                             ) {
                                 Text(
                                     text = "Đổi voucher",
                                     style = TextStyle(
                                         fontSize = 14.sp,
                                         fontWeight = FontWeight.Medium
                                     )
                                 )

                                 Spacer(modifier = Modifier.width(8.dp))

                                 Icon(
                                     painter = painterResource(id = R.drawable.ic_voucher), // hình chiếc vé như ảnh
                                     contentDescription = "voucher",
                                     modifier = Modifier.size(16.dp),
                                     tint = Color.White
                                 )
                             }

                         }

                         Spacer(modifier = Modifier.height(12.dp))

                         // rewwards 1
                         DiagonalProgressBar(50f,FFE40000,EEA1F27,EFFFFFF)


                         Spacer(modifier = Modifier.height(8.dp))

                         Row(
                             modifier = Modifier.fillMaxWidth(),
                             horizontalArrangement = Arrangement.SpaceBetween
                         ){

                         Text(
                             text = "Trailblazer",
                             style = TextStyle(
                                 fontSize = 12.sp,
                                 fontFamily = FontFamily(Font(R.font.inter)),
                                 fontWeight = FontWeight(500),
                                 color = FFAFAFAF,
                             ), modifier = Modifier.weight(1f).fillMaxWidth()
                         )

                         Text(
                             text = "Achiever",
                             style = TextStyle(
                                 fontSize = 12.sp,
                                 fontFamily = FontFamily(Font(R.font.inter)),
                                 fontWeight = FontWeight(500),
                                 color = Color(0xFFD7D7D7),
                                 textAlign = TextAlign.Right,
                             ), modifier = Modifier.weight(1f).fillMaxWidth()
                         )

                     }

                         Spacer(modifier = Modifier.height(10.dp))

                         Text(
                             text = stringResource(R.string.points_needed),
                             style = TextStyle(
                                 fontSize = 10.sp,
                                 lineHeight = 15.sp,
                                 fontFamily = FontFamily(Font(R.font.inter)),
                                 fontWeight = FontWeight(400),
                                 color = FF000000,
                             )
                         )
                     }
                 }
             }

             // 📋 Menu danh sách
             items(menuItems)  { (title, icon) ->
                 Column(
                     modifier = Modifier
                         .fillMaxWidth()
                         .clickable { /* TODO: Navigate */ }
                 ) {
                     Row(
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding( vertical = 12.dp, horizontal = 18.dp),
                         verticalAlignment = Alignment.CenterVertically
                     ) {
                         Icon(painter = painterResource(icon), contentDescription = null, modifier = Modifier.size(24.dp))

                         Text(
                             text = title,
                             style = TextStyle(
                                 fontSize = 14.sp,
                                 lineHeight = 16.8.sp,
                                 fontFamily = FontFamily(Font(R.font.inter)),
                                 fontWeight = FontWeight(400),
                                 color = FF151515,
                             ),
                             modifier = Modifier
                                 .padding(horizontal = 10.dp)
                                 .weight(1f)
                         )

                         Image(painter = painterResource(R.drawable.arrowright), contentDescription = null
                             , modifier = Modifier.size(20.dp))
                     }
                     CustomDividerColor(AEA1F27)
                 }
             }
         }
     }

}
@Composable
fun DiagonalProgressBar(progressPercent: Float, colorProgressBar: Color, backgroundProgressBar: Color, colorLine: Color) {
    val progress = progressPercent.coerceIn(0f, 100f) / 100f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .clip(RoundedCornerShape(50))
            .background(backgroundProgressBar) // Nền nhạt
    ) {
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(50))
        ) {
            val stripeWidth = 16.dp.toPx()

            val cornerRadius = size.height / 2

            val adjustedProgressWidth = (size.width * progress ).coerceAtLeast(0f)

            // Vẽ phần progress đỏ với bo tròn
            drawRoundRect(
                color = colorProgressBar,
                size = Size(adjustedProgressWidth, size.height),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )

            // Vẽ các đường sọc chéo
            for (i in -size.height.toInt()..(adjustedProgressWidth-40).toInt() step stripeWidth.toInt()) {
                drawLine(
                    color =colorLine, // nhẹ hơn chút
                    start = Offset(i.toFloat(), 0f),
                    end = Offset(i + size.height, size.height),
                    strokeWidth = 19f
                )
            }
        }

        // Icon mũi tên bên phải
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Arrow",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
                .size(12.dp)
        )
    }
}
