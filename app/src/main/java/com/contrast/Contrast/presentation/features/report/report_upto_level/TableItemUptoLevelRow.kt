package com.contrast.Contrast.presentation.features.report.report_upto_level



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentHeight

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.line.CustomDivider
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.theme.LightGrayBackground
import com.contrast.Contrast.presentation.theme.FF0967DF
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight

@Composable
fun TableItemUptoLevelRow(
    type: String = "",
    name: String = "",
    level: String = "",
    total_revenue: String = "",
    received_amount: String = "",
    modifier: Modifier = Modifier
) {
    Column {
        Row(
            modifier = modifier
                .height(IntrinsicSize.Min) // 👈 co theo chiều cao nội dung
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            if (type == "baocaothuongthangcap") {
                Box(
                    modifier = Modifier
                        .background(LightGrayBackground)
                        .width(1.dp)
                        .fillMaxHeight() // 👈 dùng fillMaxHeight để khớp hàng
                )
                Text(
                    text = name,
                    modifier = Modifier
                        .weight(2f)
                        .padding(10.dp),
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Box(
                modifier = Modifier
                    .background(LightGrayBackground)
                    .width(1.dp)
                    .fillMaxHeight()
            )
            Text(
                text = level,
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .background(LightGrayBackground)
                    .width(1.dp)
                    .fillMaxHeight()
            )
            Text(
                text = total_revenue,
                modifier = Modifier
                    .weight(2f)
                    .padding(10.dp),
                fontSize = 14.sp,
                color = Color.Black
            )
            Box(
                modifier = Modifier
                    .background(LightGrayBackground)
                    .width(1.dp)
                    .fillMaxHeight()
            )
            Text(
                text = received_amount,
                modifier = Modifier
                    .weight(2f)
                    .padding(10.dp),
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Right
            )
            Box(
                modifier = Modifier
                    .background(LightGrayBackground)
                    .width(1.dp)
                    .fillMaxHeight()
            )
        }

        CustomDividerColor()
    }
}
