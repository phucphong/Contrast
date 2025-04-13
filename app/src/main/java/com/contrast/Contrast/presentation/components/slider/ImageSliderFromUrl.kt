package com.contrast.Contrast.presentation.components.slider

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import com.google.accompanist.pager.*


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.image.NetworkImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator

import com.itechpro.domain.model.Product
import com.itechpro.domain.model.SliderHome


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSliderFromUrl(
    domain:String,
    autoScroll:Boolean = true,
    imageUrls: List<SliderHome>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()

    // 🔁 Auto scroll effect
    // 🔁 Auto scroll không phụ thuộc currentPage
    LaunchedEffect(autoScroll, imageUrls.size) {
        if (imageUrls.size > 1 && autoScroll) {
            while (true) {
                kotlinx.coroutines.delay(3000)
                val nextPage = (pagerState.currentPage + 1) % imageUrls.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(
            count = imageUrls.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) { page ->



            val fullUrl = domain.trimEnd('/') + (imageUrls[page].filetxt ?: "")

            NetworkImage(
                imageUrl = fullUrl,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = Color(0xFF00B09B),
            inactiveColor = Color.LightGray
                , modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}
