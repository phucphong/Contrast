package com.contrast.Contrast.presentation.features.evaluate.ui


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.Text
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
import coil3.compose.AsyncImage
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.image.AttachFileItem


import com.itechpro.domain.model.evaluate.EvaluateDetail

@Composable
fun EvaluateItem(
    evaluate: EvaluateDetail,
    domain: String,
    onDownloadClick: (String) -> Unit
) {
    val name = evaluate.noidung.orEmpty()
    val fullName = evaluate.nguoidanhgia.orEmpty()
    val time = evaluate.thoigiandanhgia.orEmpty()
    val fullUrl = domain.trimEnd('/') + (evaluate.anhdaidien ?: "")
    val list = evaluate.lst_dinhkem

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


    }
}
