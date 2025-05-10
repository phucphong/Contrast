package com.contrast.Contrast.presentation.components.profile

import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.logout.LogoutButton
import com.contrast.Contrast.presentation.components.profile.ui.OrderStatusItem
import com.contrast.Contrast.presentation.components.profile.ui.OrderStatusRow
import com.contrast.Contrast.presentation.components.profile.ui.ProfileHeader
import com.contrast.Contrast.presentation.components.profile.ui.ProfileOptionItem
import com.contrast.Contrast.presentation.components.profile.viewModel.ProfileViewModel
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.login.LoginViewModel
import com.contrast.Contrast.presentation.navigator.NavRoutes
import com.itechpro.domain.model.navigationEvent.HomeNavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent


@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val isRefreshing by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val navEvent by viewModel.navigationEvent.collectAsState()
    LaunchedEffect(navEvent) {
        when (val event = navEvent) {
            is SplashNaEvent.GoToLogIn -> {
                navHostController.navigate(NavRoutes.Login.route) {
                    popUpTo(NavRoutes.Logout.route) { inclusive = true }
                    launchSingleTop = true
                }
                viewModel.resetNavigation()
            }

            is SplashNaEvent.GoToRegister -> {
                navHostController.navigate(NavRoutes.Register.route) {
                    popUpTo(NavRoutes.Register.route) { inclusive = true }
                    launchSingleTop = true
                }
                viewModel.resetNavigation()
            }

            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
//        viewModel.getQrCodeEmployee()
        viewModel.getQrCodeCustomer()
        viewModel.getInfoAccount(uiState.customerId)
        viewModel.getMenuApp()
    }
    CustomSwipeRefresh(isRefreshing = isRefreshing,
        onRefresh = { //        viewModel.getQrCodeEmployee()
            viewModel.getQrCodeCustomer()
            viewModel.getInfoAccount(uiState.customerId)
            viewModel.getMenuApp()
        }) {
        Column {

            ProfileHeader(uiState.avartar,
                uiState.fullName,
                uiState.qrCode,
                uiState.agencyName,
                uiState.discount,
                uiState.isLogin,
                onLoginClick = { viewModel.onLoginClick() },
                onRegisterClick = { viewModel.onRegisterClick() })

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {


                item {
                    OrderStatusRow(uiState.oders)
                }

                items(uiState.categorys) { option ->
                    ProfileOptionItem(option)
                }


            }

            LogoutButton(onClickLogout = {
                viewModel.onLogoutClick()

            })
        }
    }
}








