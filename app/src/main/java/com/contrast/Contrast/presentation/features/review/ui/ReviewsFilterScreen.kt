package com.contrast.Contrast.presentation.features.review.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.EmptyStateScreen
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.topAppBar.CustomBackTitle
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitle
import com.contrast.Contrast.presentation.features.review.ReviewViewModel
import com.contrast.Contrast.presentation.theme.LightGrayBackground
import com.contrast.Contrast.presentation.theme.UltraLightGray
import com.itechpro.domain.enumApp.ReviewFilterType
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewsFilterScreen(navHostController: NavHostController, id: String, viewModel: ReviewViewModel = hiltViewModel()) {
    val filteredReviews by viewModel.filteredReviews.collectAsState()
    val ratingCountMap by viewModel.ratingCountMap.collectAsState()

    val filterCounts by viewModel.filterCounts.collectAsState()
    val domain by viewModel.domain.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val isRefreshing by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(50) // cho hệ thống khởi động mạng nếu vừa chuyển 4G

        viewModel.loadReview(id,"0")
    }
    Column(modifier = Modifier.background(UltraLightGray)) {


        CustomBackTitle(
            title = stringResource(R.string.review),
            tint =  Color.Black,
            textColor =  Color.Black,
            fontSize =  14.sp,

            painter = painterResource(id = R.drawable.ic_close),
            onBackPress = {navHostController.popBackStack() }
        )
        CustomDividerColor()

       CustomSwipeRefresh(
           isRefreshing = isRefreshing,
           onRefresh = {  viewModel.loadReview(id, "0") })
       {
         Column {   ReviewFilterSection(
             selectedFilter = selectedFilter,
             filterCounts = filterCounts,
             starCounts = ratingCountMap,
             onFilterSelect = { viewModel.setFilter(it) },
             onStarSelect = { viewModel.setStarFilter(it!!) }
         )
             if(filteredReviews.isNotEmpty()){

                 Column(
                     modifier = Modifier
                         .fillMaxWidth()
                         .wrapContentHeight(),
                     verticalArrangement = Arrangement.spacedBy(8.dp)
                 ) {
                     filteredReviews.forEach { item ->
                         ReviewItem(
                             review = item,
                             domain = domain,
                             isDivider = true,
                             onDownloadClick = { fileUrl ->
//                        onDownloadClick(fileUrl)
                             }
                         )
                     }
                 }
             }else{
                 EmptyStateScreen(
                     imageRes = R.drawable.no_review,
                     size = 160.dp,
                     title = stringResource(R.string.no_review_data),
                 )
             } }

       }

    }
}
