package com.contrast.Contrast.presentation.features.voucher.ownership

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.FCFCFC

import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.features.voucher.ui.VoucherItem
import com.contrast.Contrast.presentation.theme.FF151515
import com.contrast.Contrast.presentation.theme.FFD91E18
import com.contrast.Contrast.presentation.theme.FFD9D9D9
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
fun VoucherScreen() {
Column(
    modifier = Modifier
        .fillMaxSize()
        .background(FCFCFC)
) {     Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .background(FCFCFC)
) {
    Spacer(modifier = Modifier.height(30.dp))

    CustomTopAppBarBackTitle(
        title =stringResource(R.string.owned_voucher),
        FFD91E18,
        FCFCFC,
        FontWeight.Bold,
        FFD9D9D9,
        onBackClick = {  }
    )



    Spacer(modifier = Modifier.height(10.dp))

    Row(verticalAlignment = Alignment.Bottom) {

        Image(
            painter = painterResource(R.drawable.cup_1),
            contentDescription = "cup icon",
            modifier = Modifier.size(25.dp)
        )

        Text(
            text = stringResource(R.string.cup_exchange_history),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = FF151515,
            modifier = Modifier.padding(start = 5.dp)
         )
        Image(
            painter = painterResource(R.drawable.arrowright),
            contentDescription = "cup icon",
            modifier = Modifier.size(20.dp).padding(start = 5.dp)
        )
    }
    LazyColumn {
        items(vouchers) { voucher ->
            VoucherItem(voucher,false)
        }
    }
} }
}




@Preview(showBackground = true)
@Composable
fun PreviewVoucherScreen() {
    VoucherScreen()
}
