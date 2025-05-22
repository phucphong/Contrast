package com.contrast.Contrast.presentation.features.share


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatCurrency
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.TealGreen
import com.itechpro.domain.model.home.HomeUiState


@Composable
fun ParticipationResultCard(

    startDate: String = "01/05/2025",
    endDate: String = "31/05/2025",
    count: Double = 0.0,
    commission: Double = 0.0,
    revenue: Double = 0.0,
    modifier: Modifier = Modifier,
    onClickDate :()->Unit
) {
    val orangeColor = Color(0xFFFF9800)

    Column(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        // Tiêu đề + ngày
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.noRippleClickableComposable { onClickDate() }) {
            Text(
                text = stringResource(
                    R.string.participation_result_title
                ),
                fontSize = 13.sp,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )

            Text(
                text = "$startDate - $endDate",
                color = TealGreen,
                fontSize = 12.sp,
                modifier = Modifier.weight(1f).padding(start = 5.dp)
                , textAlign = TextAlign.End
            )
            Image(
                painter = painterResource(R.drawable.down),
                contentDescription = "",
                modifier = Modifier.size(25.dp).padding(5.dp)
            )
        }


        Spacer(modifier = Modifier.height(12.dp))

        // 3 chỉ số
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            ParticipationItem(
                count.formatCurrency(), stringResource(R.string.order_count), orangeColor
            )
            ParticipationItem(
                commission.formatCurrency(),
                stringResource(R.string.estimated_commission),
                orangeColor
            )
            ParticipationItem(
                revenue.formatCurrency(), stringResource(R.string.revenue), orangeColor
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(R.drawable.affilate),
            contentDescription = "",
            modifier = Modifier
                .height(90.dp)
                .fillMaxWidth()
        )
    }
}