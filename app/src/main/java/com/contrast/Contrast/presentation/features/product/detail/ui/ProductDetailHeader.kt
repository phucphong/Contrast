package com.contrast.Contrast.presentation.features.product.detail.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.theme.F6F6F6
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.presentation.theme.FF7C7C7C
import com.contrast.Contrast.presentation.theme.FFDC143C
import com.contrast.Contrast.presentation.theme.FFE40000
import com.contrast.Contrast.presentation.theme.PlaceholderGray
import com.contrast.Contrast.presentation.theme.TealGreen
import com.contrast.Contrast.presentation.theme.iOSUnderlineGray

@Preview(showBackground = true)
@Composable
fun ProductDetailHeader(
    type: String = "huuhinh",
    isFavoriteInit: Boolean,
    isOfflineMode: Boolean,
    onFavorite: (Boolean) -> Unit,
    onReportClick: () -> Unit,
    onWriteReviewClick: () -> Unit
) {


    var isFavorite by remember { mutableStateOf(isFavoriteInit) }
    Column {

Row(  modifier = Modifier
    .fillMaxWidth()
    .background(FAFAFA)
    .padding(horizontal = 16.dp, vertical = 10.dp).
    noRippleClickableComposable { onWriteReviewClick() },
    verticalAlignment = Alignment.CenterVertically) {
    Text(
    text = stringResource(R.string.write_feedback),
    fontWeight = FontWeight(450),
    fontSize = 14.sp,
    color = TealGreen
)

    Image(
        imageVector =Icons.Default.Edit,
        contentDescription = "",
        colorFilter = ColorFilter.tint(TealGreen),
        modifier = Modifier
            .size(22.dp).padding(5.dp)

    )

}
        CustomDividerColor()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (type == "huuhinh") stringResource(R.string.product_detail) else stringResource(
                    R.string.service_detail
                ),
                fontWeight = FontWeight(450),
                fontSize = 14.sp,
                color = Color.Black
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    colorFilter = if (isFavorite) ColorFilter.tint(FFDC143C) else ColorFilter.tint(FF7C7C7C) ,
                    modifier = Modifier
                        .size(20.dp)
                        .noRippleClickableComposable {
                            if(!isOfflineMode){
                                isFavorite = !isFavorite

                            }
                            onFavorite(isFavorite)
                        }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.save),
                    modifier = Modifier.noRippleClickableComposable { },
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "|",
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.report),
                    modifier = Modifier.noRippleClickableComposable { onReportClick() },
                    fontSize = 14.sp
                )
            }
        }

        CustomDividerColor()
    }
}
