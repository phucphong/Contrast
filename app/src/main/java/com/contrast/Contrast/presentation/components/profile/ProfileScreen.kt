package com.contrast.Contrast.presentation.components.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.presentation.components.copyrightInfoSection.CopyrightInfoSection

import com.contrast.Contrast.presentation.components.logout.LogoutButton
import com.contrast.Contrast.presentation.components.profile.ui.OrderStatusRow
import com.contrast.Contrast.presentation.components.profile.ui.ProfileHeader
import com.contrast.Contrast.presentation.components.profile.ui.ProfileOptionItem
import com.contrast.Contrast.presentation.components.profile.viewModel.ProfileViewModel
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.features.login.LoginViewModel
import com.contrast.Contrast.presentation.navigator.router.routes.AuthRoutes
import com.itechpro.domain.model.navigationEvent.SplashNaEvent


@RequiresApi(Build.VERSION_CODES.O)
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
                navHostController.navigate(
                    AuthRoutes.Login.withArgs(
                        isClose = event.isClose,
                    )
                ) {
                    popUpTo(0) { inclusive = true } // Xóa toàn bộ backstack
                }
                viewModel.resetNavigation()
            }

            is SplashNaEvent.GoToRegister -> {
                navHostController.navigate(AuthRoutes.Register.route) {
                    popUpTo(AuthRoutes.Register.route) { inclusive = true }
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








