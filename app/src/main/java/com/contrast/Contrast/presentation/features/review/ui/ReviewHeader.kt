package com.contrast.Contrast.presentation.features.review.ui
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.formatFloat
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.ratingbar.RatingStars

import com.contrast.Contrast.presentation.theme.FFF5F5F5
import com.contrast.Contrast.presentation.theme.TealGreen
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ReviewHeader(
    rating: Float,
    totalReviews: Int,
    onViewAllClick: () -> Unit
) {
   Column {
       Box (modifier = Modifier.size(5.dp).background(FFF5F5F5))

       Row(
           modifier = Modifier
               .fillMaxWidth().background(Color.White)

               .padding(horizontal = 16.dp, vertical = 12.dp),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Row(verticalAlignment = Alignment.CenterVertically) {
               Text(
                   text = rating.formatFloat(),
                   fontWeight = FontWeight.Bold,
                   fontSize = 14.sp
               )

               Spacer(modifier = Modifier.width(4.dp))


               RatingStars(rating=rating.toInt(),
                   onRatingChanged = { })

               Spacer(modifier = Modifier.width(4.dp))

               Text(
                   text =if(totalReviews>0) "($totalReviews)" else "",
                   fontSize = 12.sp,
                   color = Color.Black
               )
           }

           // "Xem tất cả"
           Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier.noRippleClickableComposable { onViewAllClick() }
           ) {
               Text(
                   text = stringResource(R.string.see_all),
                   fontSize = 14.sp,
                   color = TealGreen // xanh ngọc nhẹ
               )
               Spacer(modifier = Modifier.width(4.dp))
               Icon(
                   imageVector = Icons.Default.ChevronRight,
                   contentDescription = "Arrow",
                   tint = Color(0xFF00BCD4),
                   modifier = Modifier.size(16.dp)
               )
           }
       }
       CustomDividerColor()
   }
}
