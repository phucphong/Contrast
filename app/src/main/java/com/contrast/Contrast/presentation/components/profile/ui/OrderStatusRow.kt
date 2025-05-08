package com.contrast.Contrast.presentation.components.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.profile.SectionDivider


@Composable
fun OrderStatusRow() {
    Column { Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OrderStatusItem(
            icon = R.drawable.ic_edit, // drawable của bạn
            title = "Đặt hàng\nchờ xác nhận"
        )
        OrderStatusItem(
            icon = R.drawable.ic_edit,
            title = "Đơn hàng\nđã xác nhận"
        )
        OrderStatusItem(
            icon = R.drawable.ic_edit,
            title = "Đơn hàng\nđã mua"
        )
    }
        SectionDivider() }
}
