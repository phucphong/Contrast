package com.contrast.Contrast.presentation.features.opportunity.detail


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.media.AttachFileItem
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.FF0967DF
import com.contrast.Contrast.presentation.theme.TealGreen


import com.itechpro.domain.model.review.ReviewDetail

@Composable
fun AttachFileItemView(
    fileName: String,
    fileSize: String,
    personCreate: String,
    createdDate: String,
    fileUrl: String,
    onDownloadClick: (String ) -> Unit
) {
    val isImage = fileName.endsWith(".png", true) || fileName.endsWith(
        ".jpg", true
    ) || fileName.endsWith(".jpeg", true)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding( 8.dp)
    ) {
        //31446.06 kB| ADMIN| 02/06/2025 11:43

        Row(Modifier.padding(bottom =  5.dp)) {
            CustomText(text = "$fileSize kB", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.wrapContentWidth())
            CustomText(text = "$personCreate", fontSize = 12.sp, color = FF0967DF, modifier = Modifier.wrapContentWidth().padding(start = 5.dp))
            CustomText(text = "$createdDate", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.wrapContentWidth().padding(start = 5.dp))

        }

        CustomText(text = "$fileName", fontSize = 13.sp, fontWeight = FontWeight.Medium,modifier =Modifier.padding(bottom =  5.dp))


        if (isImage) {
            AsyncImage(
                model = fileUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

        CustomText(text = stringResource(R.string.dowload),
            color = Color(0xFF00B2FF),
            fontSize = 13.sp,

            modifier = Modifier
                .padding(vertical = 4.dp)
                .noRippleClickableComposable { onDownloadClick(fileUrl) })

        CustomDividerColor()
    }
}
