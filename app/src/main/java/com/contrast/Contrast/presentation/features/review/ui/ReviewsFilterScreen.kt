package com.contrast.Contrast.presentation.features.review.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.features.review.ReviewViewModel
import com.itechpro.domain.enumApp.ReviewFilterType
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewsFilterScreen(navHostController: NavHostController, id: String, viewModel: ReviewViewModel = hiltViewModel()) {
    val reviews by viewModel.reviews.collectAsState()

    val domain by viewModel.domain.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()

    LaunchedEffect(Unit) {
        delay(100) // cho hệ thống khởi động mạng nếu vừa chuyển 4G

        viewModel.loadReview(id,"1000")
    }
    Column {

        CustomTopAppBarBackTitle(
            title = stringResource(id = R.string.evaluates),
            Color.Red,
            onBackClick = { navHostController.popBackStack() }
        )


        ReviewFilterSection(
            selectedFilter = selectedFilter,
            filterCounts = mapOf(
                ReviewFilterType.ALL to 1,
                ReviewFilterType.COMMENT_ONLY to 1,
                ReviewFilterType.IMAGE_ONLY to 0
            ),
            starCounts = viewModel.ratingCountMap,
            onFilterSelect = { viewModel.setFilter(it) },
            onStarSelect = { viewModel.setStarFilter(it!!)

            }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            reviews.forEach { item ->
                ReviewItem(
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
