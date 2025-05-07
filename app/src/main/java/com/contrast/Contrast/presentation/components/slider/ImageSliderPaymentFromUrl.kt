package com.contrast.Contrast.presentation.components.slider

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.base64.Base64Image

import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.google.accompanist.pager.*
import com.itechpro.domain.model.payment.InfoPayment

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSliderPaymentFromUrl(
    domain: String,
    autoScroll: Boolean = true,
    indicator: Boolean = true,
    slides: List<InfoPayment>,
    modifier: Modifier = Modifier,
    onDownloadClick: ((String) -> Unit)
) {
    val pagerState = rememberPagerState()

    LaunchedEffect(autoScroll, slides.size) {
        if (slides.size > 1 && autoScroll) {
            while (true) {
                kotlinx.coroutines.delay(3000)
                val nextPage = (pagerState.currentPage + 1) % slides.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(
            count = slides.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            val raw = slides[page].maqr.orEmpty()
            if (raw.startsWith("data:image")) {
                Base64Image(
                    base64String = raw,
                    modifier = Modifier.fillMaxSize(),
                    onDownloadClick={
                        onDownloadClick(raw)
                    }
                )
            } else {
                val fullUrl = domain.trimEnd('/') + raw
                NetworkImage(
                    model = fullUrl,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        if (indicator) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color(0xFF00B09B),
                inactiveColor = Color.LightGray,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}
