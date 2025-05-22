package com.contrast.Contrast.presentation.components.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.presentation.components.logout.LogoutButton
import com.contrast.Contrast.presentation.components.profile.ui.OrderStatusRow
import com.contrast.Contrast.presentation.components.profile.ui.ProfileHeader
import com.contrast.Contrast.presentation.components.profile.ui.ProfileOptionItem
import com.contrast.Contrast.presentation.components.profile.viewModel.ProfileViewModel
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.features.login.LoginViewModel
import com.contrast.Contrast.presentation.navigator.routers.AuthRoutes
import com.contrast.Contrast.presentation.navigator.routers.ProductRoutes
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import com.itechpro.domain.model.profile.ProfileNaEvent
import com.contrast.Contrast.presentation.navigator.routers.ProfileRoutes
import com.itechpro.domain.model.navigationEvent.ProductNavEvent


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),

    ) {
    val isRefreshing by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.navEvent) {
        when (val event = state.navEvent) {
            is SplashNaEvent.GoToLogIn -> {
                navHostController.navigate(
                    AuthRoutes.Login.withArgs(
                        isClose = event.isClose,
                    )
                ) {
                    popUpTo(0) { inclusive = true } // hoặc pop đến "splash" nếu có
                    launchSingleTop = true
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

            is ProfileNaEvent.GoToShareQrcode -> {
                navHostController.navigate(
                    ProfileRoutes.ShareQrcode.withArgs(

                        title = event.title,
                    )
                )

                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToShareProduct -> {
                navHostController.navigate(
                    ProfileRoutes.ShareProduct.withArgs(

                        title = event.title,
                    )
                )

                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToInCome -> {
                navHostController.navigate(
                    ProfileRoutes.InCome.withArgs(

                        title = event.title,
                    )
                )

                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToProductViewSave -> {
                navHostController.navigate(
                    ProfileRoutes.ProductViewSave.withArgs(
                        type = event.type,
                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToServiceProgress -> {
                navHostController.navigate(
                    ProfileRoutes.ServiceProgress.withArgs(

                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToServiceCalendar -> {
                navHostController.navigate(
                    ProfileRoutes.ServiceCalendar.withArgs(

                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToOderType -> {
                navHostController.navigate(
                    ProfileRoutes.OderType.withArgs(
                        type = event.type,
                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToSpaAtHome -> {
                navHostController.navigate(
                    ProfileRoutes.SpaAtHome.withArgs(

                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToAgency -> {
                navHostController.navigate(
                    ProfileRoutes.Agencys.withArgs(

                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToOpportunity -> {
                navHostController.navigate(
                    ProfileRoutes.Opportunity.withArgs(

                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToPersonalConsumptionSales -> {
                navHostController.navigate(
                    ProfileRoutes.PersonalConsumptionSales.withArgs(

                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToPassiveCommissionReport -> {
                navHostController.navigate(
                    ProfileRoutes.PassiveCommissionReport.withArgs(

                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToSalesReportByAgency -> {
                navHostController.navigate(
                    ProfileRoutes.SalesReportByAgency.withArgs(

                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }

            is ProfileNaEvent.GoToRankAdvancementBonusReport -> {
                navHostController.navigate(
                    ProfileRoutes.RankAdvancementBonusReport.withArgs(
                        type = event.type,
                        title = event.title,
                    )
                )
                viewModel.resetNavigation()
            }


            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
//        viewModel.getQrCodeEmployee()
        viewModel.getQrCodeCustomer()
        viewModel.getInfoAccount(state.customerId)
        viewModel.getMenuApp()
    }
    CustomSwipeRefresh(isRefreshing = isRefreshing,
        onRefresh = { //        viewModel.getQrCodeEmployee()
            viewModel.getQrCodeCustomer()
            viewModel.getInfoAccount(state.customerId)
            viewModel.getMenuApp()
        }) {
        Column(Modifier.padding(bottom = 80.dp)) {
            ProfileHeader(state.avartar,
                state.fullName,
                state.qrCode,
                state.agencyName,
                state.discount,
                state.isLogin,
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
                    OrderStatusRow(state.oders,onProfileClick = { viewModel.onProfileClick(it) })
                }
                items(state.categorys) { option ->
                    ProfileOptionItem(option, onProfileClick = { viewModel.onProfileClick(it) })
                }
            }

            LogoutButton(onClickLogout = {
                viewModel.onLogoutClick()

            })

        }
    }
}








