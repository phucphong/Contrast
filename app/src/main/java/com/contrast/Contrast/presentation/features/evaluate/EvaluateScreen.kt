package com.contrast.Contrast.presentation.features.evaluate

import androidx.compose.foundation.lazy.LazyColumn
import com.contrast.Contrast.presentation.features.evaluate.ui.EvaluateItem
import com.itechpro.domain.model.evaluate.EvaluateDetail
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itechpro.domain.model.Product
import com.itechpro.domain.model.product.AttachFile

@Composable
fun EvaluateScreen(
    evaluates: List<EvaluateDetail>,
    domain: String,
    onDownloadClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        evaluates.forEach { item ->
            EvaluateItem(
                evaluate = item,
                domain = domain,
                onDownloadClick = { fileUrl ->
                    onDownloadClick(fileUrl)
                }
            )
        }
    }
}
