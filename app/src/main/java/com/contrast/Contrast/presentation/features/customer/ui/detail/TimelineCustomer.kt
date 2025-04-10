package com.contrast.Contrast.presentation.features.customer.ui.detail




import android.content.res.Configuration
import androidx.compose.foundation.background
import com.itechpro.domain.model.InfoDetail
import androidx.compose.foundation.layout.*


import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.checkbox.CheckBoxColor
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.PlaceholderGray
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.theme.iOSUnderlineGray

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun TimelineCustomer(info: InfoDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Hàng 1: thời gian + % hoàn thành
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomText(
                text = "10/04/2025 21:06",
                color = PlaceholderGray,
                fontSize = 12.sp
            )
            Box(
                modifier = Modifier
                    .background(Color.Red)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                CustomText(
                    text = "0%",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Hàng 2: mã KH + nội dung
        CustomText(
            text = "KH20250328054536 - gửi hợp đồng",
            color = TealGreen,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Hàng 3: KH + NV
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomText(
                text = "KH: ÂU VĂN TRƯỜNG",
                color = PlaceholderGray,
                fontSize = 13.sp
            )
            CustomText(
                text = "NV: Admin",
                color = PlaceholderGray,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(6.dp))
        CustomDividerColor(iOSUnderlineGray)
    }
}
