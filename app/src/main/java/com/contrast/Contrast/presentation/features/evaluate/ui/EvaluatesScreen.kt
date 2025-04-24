package com.contrast.Contrast.presentation.features.evaluate.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.features.evaluate.EvaluateViewModel
import com.itechpro.domain.enumApp.ReviewFilterType
import com.itechpro.domain.model.evaluate.EvaluateDetail

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EvaluatesScreen(navHostController: NavHostController, id: String, viewModel: EvaluateViewModel = hiltViewModel()) {
    val reviews by viewModel.reviews.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val currentFilter by viewModel.filterType.collectAsState() // 👈 cần StateFlow cho filterType

    val filterOptions = listOf(
        "Tất cả" to ReviewFilterType.ALL,
        "Kèm bình luận" to ReviewFilterType.COMMENT_ONLY,
        "Kèm hình ảnh" to ReviewFilterType.IMAGE_ONLY
    )

    Column {
        val currentFilter by viewModel.filterType.collectAsState()
        CustomTopAppBarBackTitle(
            title = stringResource(id = R.string.evaluates),
            Color.Red,
            onBackClick = { navHostController.popBackStack() }
        )


        LazyRow(modifier = Modifier.padding(8.dp)) {
            items(filterOptions.size) { index ->
                val (label, type) = filterOptions[index]
                FilterChip(
                    label = label,
                    isSelected = currentFilter == type,
                    onClick = { viewModel.setFilter(type) }
                )
            }
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            reviews.forEach { item ->
                EvaluateItem(
                    evaluate = item,
                    domain = domain,
                    onDownloadClick = { fileUrl ->
//                        onDownloadClick(fileUrl)
                    }
                )
            }
        }
    }
}

@Composable
fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .clickable(onClick = onClick)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text = label, color = Color.White)
    }
}
