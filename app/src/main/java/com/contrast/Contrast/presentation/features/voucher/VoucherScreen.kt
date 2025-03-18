package com.contrast.Contrast.presentation.features.voucher

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.contrast.Contrast.R
import com.contrast.Contrast.core.RedCustom
import com.contrast.Contrast.presentation.components.topAppBar.CustomTitleBar

// Data model
data class Voucher(
    val title: String,
    val expiryDate: String,
    val color: Color,
    val type: String // New field to determine the type of voucher
)

// Sample list of vouchers
val vouchers = listOf(
    Voucher("Giảm giá ₫100,000 cho hóa đơn thứ 3 trong tuần", "Hết hạn vào 31/01/2026", Color(0xFFFFA000), "Standard"),
    Voucher("Giảm ₫10,000 cho hóa đơn đầu tiên", "Hết hạn vào 31/01/2026", Color(0xFFFFA000), "Standard"),
    Voucher("Contrast tặng bạn - Giảm giá ₫100,000 cho hóa đơn tiếp theo", "Hết hạn vào 31/01/2026", Color(0xFFD32F2F), "Special")
)
@Preview(showBackground = true)
@Composable
fun VoucherScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
  Spacer(modifier = Modifier.height(40.dp))

        CustomTitleBar(
            title = "Voucher đang sở hữu",
            onBackPress = { /* Xử lý quay lại */ }
        )

        LazyColumn {
            items(vouchers) { voucher ->
                VoucherItem(voucher)
            }
        }
    }
}

@Composable
fun VoucherItem(voucher: Voucher) {
  Column {
      Box(modifier = Modifier.fillMaxHeight().padding(8.dp)) {
          Image(painter = painterResource(R.drawable.vourcher_special),
              contentDescription = "", )

  Row() {
      Box(
          modifier = Modifier
          .width(30.dp)
              .fillMaxHeight()
          .background(Color.Transparent)
          )
      Row(

      modifier = Modifier
          .fillMaxWidth(0.95f)
     
          .background(Color.White)
          .padding(8.dp),
          horizontalArrangement = Arrangement.Center

      ) {


      Spacer(modifier = Modifier.width(8.dp))

      Column(modifier = Modifier.weight(1f)) {
          Text(text = voucher.title, fontSize = 14.sp, fontWeight = FontWeight.Medium)
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = voucher.expiryDate, fontSize = 12.sp, color = Color.Gray)

      }

      Button(
          onClick = {},
          colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.padding(start = 8.dp)
      ) {
          Text(text = "Áp dụng", color = Color.White, fontSize = 12.sp)
      }
  } }


      }


      Spacer(modifier = Modifier.height(15.dp))
  }
}

@Preview(showBackground = true)
@Composable
fun PreviewVoucherScreen() {
    VoucherScreen()
}
