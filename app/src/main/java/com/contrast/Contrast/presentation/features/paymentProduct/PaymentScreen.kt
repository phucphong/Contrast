package com.contrast.Contrast.presentation.features.paymentProduct

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel


import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.line.CustomDividerColor


import com.contrast.Contrast.presentation.components.searchBar.TopTextNotificationShare
import com.contrast.Contrast.presentation.components.segment_tab.SegmentTabLocal
import com.contrast.Contrast.presentation.components.slider.ImageSliderPaymentFromUrl
import com.contrast.Contrast.presentation.components.toast.CustomToast
import com.contrast.Contrast.presentation.components.toast.toastCollect
import com.contrast.Contrast.presentation.components.topAppBar.TopBackTittleHome

import com.contrast.Contrast.presentation.features.cart.CartViewModel


import com.contrast.Contrast.presentation.features.review.ReviewViewModel

import com.contrast.Contrast.presentation.features.product.viewmodel.ProductViewModel
import com.contrast.Contrast.presentation.navigator.NavRoutes


import com.contrast.Contrast.presentation.theme.FFFFFFFF
import com.contrast.Contrast.presentation.theme.TealGreen

import com.itechpro.domain.model.ToastPosition
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.HomeNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent


@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentScreen(
    navHostController: NavHostController,
    totalIntoMoney: String,
    oderKey: String,
    idOder: String,
    discount: String,
    address: String,
    isOpportunity: String,
    viewModel: PaymentViewModel = hiltViewModel()

) {
    val selectedTab by viewModel.selectedTabIndex.collectAsState()
    val categoryName by viewModel.selectedCategoryName.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    var toastMessage by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) }
    val navEvent by viewModel.navigationEvent.collectAsState()
    val context = LocalContext.current
// Thu thập từ nhiều ViewModel
    viewModel.notificationToast.toastCollect {
        if (!showToast) {
            toastMessage = it
            showToast = true
        }
    }


    LaunchedEffect(Unit) {

        viewModel.getInfoPayment(totalIntoMoney, oderKey)
        viewModel.generateCategory(isOpportunity.toBoolean())
    }

    LaunchedEffect(navEvent) {
        when (val event = navEvent) {


            is HomeNavEvent.GoToHome -> {
                navHostController.navigate(NavRoutes.AffiliateHome.route) {
                    popUpTo(NavRoutes.AffiliateHome.route) { inclusive = true }
                    launchSingleTop = true
                }
                viewModel.resetNavigation()
            }



            else -> Unit
        }
    }

    Box {

        Column(modifier = Modifier.fillMaxSize()) {

            TopBackTittleHome(painter = painterResource(R.drawable.quaylai),

                placeholder = stringResource(R.string.info_payment) ,
                onBackStack = { navHostController.popBackStack() },
                onHomeClick = { viewModel.gotoHome() }
            )
            CustomDividerColor()

            if (uiState.tabs.size > 1&&uiState.typeAccount!="khachhang") {
                SegmentTabLocal(
                    tabs = uiState.tabs,
                    selectedTab = selectedTab,

                    onTabSelected = { viewModel.updateSelectedTab(it) }
                )
            }

            if (uiState.qrCodes.isNotEmpty()&&categoryName=="qrcode") {
                ImageSliderPaymentFromUrl(
                    domain = uiState.domain,
                    autoScroll = false,
                    slides = uiState.qrCodes,
                    modifier = Modifier.fillMaxSize().padding(10.dp),
                    onDownloadClick={
                        viewModel.saveBase64Image(it, context)
                    }
                )
            }

            if (categoryName=="payment_info") {
//                ImageSliderPaymentFromUrl(
//                    domain = uiState.domain,
//                    autoScroll = false,
//                    slides = uiState.qrCodes,
//                    modifier = Modifier.fillMaxSize().padding(10.dp),
//                    onDownloadClick={
//                        viewModel.saveBase64Image(it, context)
//                    }
//                )
            }

        }
        if (showToast) {
            CustomToast(
                message = toastMessage,
                textAlign = TextAlign.Center,
                background = FFFFFFFF,
                textColor = Color.Black,
                showToast = true,
                toastPosition = ToastPosition.CENTER,
                durationMillis = 2000,
                onDismiss = {
                    showToast = false
                },
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .wrapContentHeight()
                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(8.dp)) // ✅ Đổ bóng
                    .clip(RoundedCornerShape(8.dp)) // ✅ Bo góc sau khi đổ bóng
                    .background(FFFFFFFF) // ✅ Bắt buộc: set lại màu nền sau khi clip
                    .border(1.dp, FFFFFFFF, RoundedCornerShape(8.dp))

            )

        }
    }
}


