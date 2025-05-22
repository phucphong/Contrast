package com.contrast.Contrast.presentation.features.in_come


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable


@Composable
fun OrderItemViewIncome(
    orderKey: String,
    status: String,
    totalCount: String,
    actualCommission: String,
    createDate: String,
    showCoin: Boolean = false,
    onToggleClick: () -> Unit = {},
    isExpanded: Boolean = false,
    onArrowClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 10.dp)
    ) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = orderKey,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                textAlign = TextAlign.Start
            )

            Text(
                text = status,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp),
                textAlign = TextAlign.End
            )
        }

        // Content (RecyclerView equivalent)
        Box(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
        ) {
            content()
        }

        // Toggle view
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .noRippleClickableComposable { onToggleClick() }
                .padding(vertical = 10.dp)
        ) {
            Divider(color = Color(0xFFDDDDDD), thickness = 1.dp)
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isExpanded) "Thu gọn" else "Xem thêm",
                    color = Color(0xFF666666),
                    fontSize = 14.sp
                )
                Icon(
                    painter = painterResource(id = R.drawable.down),
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp)
                        .padding(start = 4.dp)
                )
            }
        }

        // Total count and commission row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = totalCount,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = actualCommission,
                    color = Color.Black,
                    fontSize = 12.sp
                )
                if (showCoin) {
                    Icon(
                        painter = painterResource(id = R.drawable.coin),
                        contentDescription = null,
                        modifier = Modifier
                            .size(15.dp)
                            .padding(start = 4.dp)
                    )
                }
            }
        }

        // Create date
        Text(
            text = createDate,
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )

        // Bottom spacing
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = Color(0xFFE0E0E0))
        )
    }
}