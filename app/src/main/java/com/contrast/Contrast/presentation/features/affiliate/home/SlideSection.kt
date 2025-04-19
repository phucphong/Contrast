package com.contrast.Contrast.presentation.features.affiliate.home

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.slider.ImageSliderFromUrl
import com.itechpro.domain.model.SliderHome

@Composable
fun SlideSection(slides: List<SliderHome>, domain: String) {
    if (slides.isNotEmpty()) {
        ImageSliderFromUrl(
            domain = domain,
            autoScroll = true,
            slides = slides,
            modifier = Modifier.height(220.dp)
        )
    }
}
