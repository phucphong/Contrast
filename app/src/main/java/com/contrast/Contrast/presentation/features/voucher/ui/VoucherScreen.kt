package com.contrast.Contrast.presentation.features.voucher.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.contrast.Contrast.R
import com.contrast.Contrast.core.FFFCFCFC

import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarTittleBack

// Data model
data class Voucher(
    val title: String,
    val expiryDate: String,
    val color: Color,
    val type: String // New field to determine the type of voucher
)

// Sample list of vouchers
val vouchers = listOf(
    Voucher(
        "Giảm giá ₫100,000 cho hóa đơn thứ 3 trong tuần",
        "Hết hạn vào 31/01/2026",
        Color(0xFFFFA000),
        "Standard"
    ), Voucher(
        "Giảm ₫10,000 cho hóa đơn đầu tiên", "Hết hạn vào 31/01/2026", Color(0xFFFFA000), "Standard"
    ), Voucher(
        "Contrast tặng bạn - Giảm giá ₫100,000 cho hóa đơn tiếp theo",
        "Hết hạn vào 31/01/2026",
        Color(0xFFD32F2F),
        "Special"
    )
)

@Preview(showBackground = true)
@Composable
fun VoucherScreen() {
Column(
    modifier = Modifier
        .fillMaxSize()
        .background(FFFCFCFC)
) {     Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .background(FFFCFCFC)
) {
    Spacer(modifier = Modifier.height(30.dp))

    CustomTopAppBarTittleBack(
        title =stringResource(R.string.owned_voucher),
        Color.Red,
        FFFCFCFC,
        FontWeight.Bold,
        Color.Red,
        onBackClick = {  }
    )



    Spacer(modifier = Modifier.height(10.dp))
    LazyColumn {
        items(vouchers) { voucher ->
            VoucherItem(voucher)
        }
    }
} }
}
@Composable
fun VoucherItem(voucher: Voucher) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() // Đảm bảo Box không bị ép chiều cao
            .padding(vertical = 8.dp, horizontal = 5.dp)
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
                Box(
                    Modifier
                        .width(59.dp)
                        .height(15.dp)
                        .background(color = Color(0xFFD91E18), shape = RoundedCornerShape(size = 31.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Áp dụng",
                        style = TextStyle(
                            fontSize = 9.sp,
                            fontFamily = FontFamily(Font(R.font.inter_18pt_medium)),
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



@Preview(showBackground = true)
@Composable
fun PreviewVoucherScreen() {
    VoucherScreen()
}
