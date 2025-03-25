package com.contrast.Contrast.presentation.components.slider


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.contrast.Contrast.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(clip: Dp) {
    val images = listOf(
        R.drawable.slider_1,
        R.drawable.slider_2,
        R.drawable.slider_3,
        R.drawable.slider_4,
        R.drawable.slider_2,
        R.drawable.slider_3,
        R.drawable.slider_4
    )

    val pagerState = rememberPagerState(pageCount = { images.size })

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000L)
            val nextPage = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(clip)) // ✅ Bo tròn 4 góc 10dp
    ) {
        /** 🔹 HorizontalPager (Slider) */
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(10.dp)) // ✅ Bo tròn 4 góc 10dp
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = "Slider Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(10.dp)) // ✅ Bo tròn 4 góc 10dp
            )
        }

        /** 🔹 Indicator (Hiển thị trên ảnh, cách bottom 15.dp) */
        CustomPageIndicator(
            pagerState = pagerState,
            pageCount = images.size,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 15.dp)
        )
    }
}

/** 🔹 Custom Indicator */
@Composable
fun CustomPageIndicator(pagerState: PagerState, pageCount: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == pagerState.currentPage
            val color = if (isSelected) Color.White else Color.LightGray
            val size = if (isSelected) 15.dp else 15.dp

            Box(
                modifier = Modifier
                    .size(size)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp)) // ✅ Bo tròn 10dp cho Indicator nếu cần
                    .background(color)
            )
        }
    }
}
