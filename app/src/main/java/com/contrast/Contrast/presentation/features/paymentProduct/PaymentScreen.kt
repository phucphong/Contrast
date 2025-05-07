package com.contrast.Contrast.presentation.features.paymentProduct



import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

import androidx.navigation.NavHostController
import com.contrast.Contrast.R


import com.contrast.Contrast.presentation.components.searchBar.TopTextNotificationShare
import com.contrast.Contrast.presentation.components.toast.CustomToast
import com.contrast.Contrast.presentation.components.toast.toastCollect

import com.contrast.Contrast.presentation.features.cart.CartViewModel


import com.contrast.Contrast.presentation.features.review.ReviewViewModel

import com.contrast.Contrast.presentation.features.product.viewmodel.ProductViewModel


import com.contrast.Contrast.presentation.theme.FFFFFFFF
import com.contrast.Contrast.presentation.theme.TealGreen

import com.itechpro.domain.model.ToastPosition



@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaymentScreen(
    navHostController: NavHostController,
    totalIntoMoney: String,
    discount: String,
    address: String,
    isOpportitue: String,
    viewModel: CartViewModel = hiltViewModel()

) {

    var type by remember { mutableStateOf("huuhinh") }
    var bookService by remember { mutableStateOf(false) }
    var isShowQuantity by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf(false) }

// Thu thập từ nhiều ViewModel
    viewModel.notificationToast.toastCollect {
        if (!showToast) {
            toastMessage = it
            showToast = true
        }
    }





    Box {

        Column(modifier = Modifier.fillMaxSize()) {

            TopTextNotificationShare(painter = painterResource(R.drawable.quaylai),

                placeholder = if (type == "huuhinh") stringResource(R.string.product_detail) else stringResource(
                    R.string.service_detail
                ),

                totalCartItems = 0,
                onShareClick = {
                },
                onCartClick = {  },
                onBackStack = { navHostController.popBackStack() })

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

@RequiresApi(Build.VERSION_CODES.O)
fun callApi(
    viewModel: ProductViewModel,
    reviewViewModel: ReviewViewModel,
    cartViewModel: CartViewModel,
    id: String,
    idUnit: String
) {
    viewModel.loadData(id, idUnit)
    reviewViewModel.loadReview(id, "3")
    cartViewModel.getCarts(false)
    viewModel.getProductsOther(id)
}

