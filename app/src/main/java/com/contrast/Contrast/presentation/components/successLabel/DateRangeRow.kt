package com.contrast.Contrast.presentation.components.successLabel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.FFAFAFAF


@Composable
fun DateRangeRow(
    startDate: String,
    endDate: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 5.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.clock),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .padding(3.dp), colorFilter = ColorFilter.tint(FFAFAFAF)
            )
            CustomText(
                text =startDate,
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight(500),
                modifier = Modifier.padding(5.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f).padding(end = 5.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                painter = painterResource(R.drawable.clock),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .padding(3.dp), colorFilter = ColorFilter.tint(FFAFAFAF)
            )
            CustomText(
                text = endDate,
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight(500),
                modifier = Modifier.padding(5.dp)
            )
        }
    }
}
