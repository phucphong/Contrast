package com.contrast.Contrast.presentation.features.voucher.redeem.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.presentation.theme.*

@Composable
fun PointsAndVoucherCard(
    points: Int,
    title: String,
    cupIcon: Int,
    borderColor: Color,
    voucherIcon: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(100.dp)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .background(
                color = FCFCFC,
                shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
            )
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 30.dp, top = 10.dp, bottom = 10.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = FF7C7C7C,
                    modifier = Modifier.wrapContentWidth().padding(bottom = 10.dp, top = 10.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = points.toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = FF151515,
                        modifier = Modifier.wrapContentWidth().padding(end = 10.dp)
                    )
                    Image(
                        painter = painterResource(cupIcon),
                        contentDescription = "cup icon",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Image(
                painter = painterResource(voucherIcon),
                contentDescription = "voucher icon",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(top = 10.dp)
            )
        }
    }
}