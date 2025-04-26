package com.contrast.Contrast.presentation.features.review.ui


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.media.AttachFileItem
import com.contrast.Contrast.presentation.components.line.CustomDividerColor


import com.itechpro.domain.model.review.ReviewDetail

@Composable
fun ReviewItem(
    review: ReviewDetail,
    isDivider: Boolean =false,
    domain: String,
    onDownloadClick: (String) -> Unit
) {
    val name = review.noidung.orEmpty()
    val fullName = review.nguoidanhgia.orEmpty()
    val time = review.thoigiandanhgia.orEmpty()
    val rank = review.diem ?: 0
    val fullUrl = domain.trimEnd('/') + (review.anhdaidien ?: "")
    val list = review.lst_dinhkem

    Column(

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = fullUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error= painterResource(R.drawable.noimagevetical),
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .padding(5.dp)
            )

            Row (modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = fullName,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    color = Color.Black
                )
                Text(
                    text = time,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray, modifier = Modifier.padding(start = 10.dp)
                )
            }
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            (1..5).forEach { star ->
                Icon(
                    imageVector = if (star <= rank) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Star $star",
                    tint = if (star <= rank) Color.Black else Color.Gray, // vàng & xám
                    modifier = Modifier
                        .size(16.dp)

                )
            }
        }
        Text(
            text = name,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(16.dp)
        )
        if (list.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                list.forEach { item ->
                    AttachFileItem(
                        attachFile = item,
                        domain = domain,
                        onDownloadClick={onDownloadClick(it)}
                    )
                }
            }
        }
        CustomDividerColor()

    }
}
