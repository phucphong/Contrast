package com.contrast.Contrast.presentation.features.in_come
import com.contrast.Contrast.presentation.features.share.ParticipationResultCard


import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.media.NetworkImage


@Composable
fun ProductItemView(
    fullUrl: String ="",
    name: String,
    count: String,
    commission: String,
    showCoin: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {


            NetworkImage(
                model = fullUrl,
                modifier = Modifier
                    .size(60.dp)
                    .padding(10.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Dòng tên + số lượng
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = name,
                        color = Color.Black,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 5.dp, vertical = 5.dp)
                    )
                    Text(
                        text = count,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }

                // Dòng hoa hồng + icon coin
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = commission,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                    if (showCoin) {
                        Icon(
                            painter = painterResource(id = R.drawable.coin),
                            contentDescription = null,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }

        // Line dưới cùng
        CustomDividerColor()
    }
}
