package com.contrast.Contrast.presentation.features.main

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.R
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun OnboardingScreen(onFinish: () -> Unit = {}) {
    val pagerState = rememberPagerState { 4 } // Số trang là cố định (4 trang)
    val coroutineScope = rememberCoroutineScope()

    // Tạo danh sách trang bên trong @Composable
    val pages = listOf(
        OnboardingData(R.drawable.ic_onboarding1, stringResource(R.string.onboarding_title_1), stringResource(R.string.onboarding_desc_1)),
        OnboardingData(R.drawable.ic_onboarding2, stringResource(R.string.onboarding_title_2), stringResource(R.string.onboarding_desc_2)),
        OnboardingData(R.drawable.ic_onboarding3, stringResource(R.string.onboarding_title_3), stringResource(R.string.onboarding_desc_3)),
        OnboardingData(R.drawable.ic_onboarding4, stringResource(R.string.onboarding_title_4), stringResource(R.string.onboarding_desc_4))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween // Giúp nút không bị mất
    ) {
        // ViewPager (Onboarding Content)
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f) // Giúp nội dung co dãn đúng
        ) { page ->
            OnboardingPage(pages[page]) // Truyền dữ liệu trang hiện tại
        }

        // Indicators (Dots)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { index ->
                Indicator(isSelected = pagerState.currentPage == index)
            }
        }

        // Button "Tiếp tục" hoặc "Khám phá ngay"
        Button(
            onClick = {
                coroutineScope.launch {
                    if (pagerState.currentPage < pages.size - 1) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        onFinish()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
        ) {
            Text(
                text = if (pagerState.currentPage == pages.size - 1)
                    stringResource(R.string.finish_button)
                else
                    stringResource(R.string.next_button),
                color = Color.White
            )
        }
    }
}

@Composable
fun OnboardingPage(onboardingData: OnboardingData) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = onboardingData.image), contentDescription = "Onboarding Image")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = onboardingData.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = onboardingData.description,
            fontSize = 15.sp,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(if (isSelected) 10.dp else 6.dp)
            .background(if (isSelected) Color.Black else Color.Gray, shape = MaterialTheme.shapes.small)
    )
}

data class OnboardingData(val image: Int, val title: String, val description: String)
