package com.contrast.Contrast.presentation.features.review

import com.itechpro.domain.model.review.ReviewDetail
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.features.review.ui.ReviewItem

@Composable
fun ReviewScreen(
    reviews: List<ReviewDetail>,
    domain: String,
    onDownloadClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        reviews.forEach { item ->
            ReviewItem(
                review = item,
                domain = domain,
                onDownloadClick = { fileUrl ->
                    onDownloadClick(fileUrl)
                }
            )
        }
    }
}
