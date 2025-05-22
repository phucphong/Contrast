package com.contrast.Contrast.presentation.features.shareQrcode



import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.presentation.components.alertDialog.CustomOkAlertDialog
import com.contrast.Contrast.presentation.components.base64.decodeBase64ToBitmap
import com.contrast.Contrast.presentation.components.line.CustomDividerColor
import com.contrast.Contrast.presentation.components.media.NetworkImage
import com.contrast.Contrast.presentation.components.profile.viewModel.ProfileViewModel
import com.contrast.Contrast.presentation.components.searchBar.TopSearchNotificationCart
import com.contrast.Contrast.presentation.components.searchDialog.SearchConditionDialog
import com.contrast.Contrast.presentation.components.segment_tab.SegmentTabLocal
import com.contrast.Contrast.presentation.components.swiperefresh_custom.CustomSwipeRefresh
import com.contrast.Contrast.presentation.components.tab.TabBarRowLocal
import com.contrast.Contrast.presentation.components.topAppBar.BackSearchInfomation
import com.contrast.Contrast.presentation.components.topAppBar.TopBackTittleHome
import com.contrast.Contrast.presentation.features.affiliate.home.viewModel.HomeAffiliateViewModel
import com.contrast.Contrast.presentation.features.cart.CartViewModel
import com.contrast.Contrast.presentation.features.notification.NotificationViewModel
import com.contrast.Contrast.presentation.features.product.ui.ProductRow
import com.contrast.Contrast.presentation.features.share.viewModel.ShareProductViewModel
import com.contrast.Contrast.presentation.navigator.routers.AuthRoutes
import com.contrast.Contrast.presentation.navigator.routers.CartRoutes
import com.contrast.Contrast.presentation.navigator.routers.MainRoutes
import com.contrast.Contrast.presentation.navigator.routers.NotificationRoutes
import com.contrast.Contrast.presentation.navigator.routers.ProductRoutes
import com.contrast.Contrast.presentation.navigator.routers.ServiceRequestRoutes
import com.contrast.Contrast.presentation.theme.FAFAFA
import com.contrast.Contrast.utils.Util
import com.itechpro.domain.model.DateFieldType
import com.itechpro.domain.model.navigationEvent.CartNavEvent
import com.itechpro.domain.model.navigationEvent.HomeNavEvent
import com.itechpro.domain.model.navigationEvent.NotificationNavEvent
import com.itechpro.domain.model.navigationEvent.ProductNavEvent
import com.itechpro.domain.model.navigationEvent.SplashNaEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShareQrcodeScreen(
    navHostController: NavHostController,
    title: String,
    viewModel: ProfileViewModel = hiltViewModel(),


    ) {
    val state by viewModel.state.collectAsState()


    var content by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        delay(100)

        val url = Util.extractAfterScheme(state.domain)
        val http = Util.extractBeforeScheme(state.domain)
        content = "${state.employeeId}-${state.employeeName}-$url-$http://"
        viewModel.getQrCodeContent(content)
        viewModel.loadCategory()

    }


    LaunchedEffect(state.navEvent) {
        when (val event = state.navEvent) {

            is HomeNavEvent.GoToHome -> {
                navHostController.navigate(MainRoutes.Main.route) {
                    popUpTo(MainRoutes.Main.route) { inclusive = true }
                    launchSingleTop = true
                }
                viewModel.resetNavigation()
            }



            else -> Unit
        }
    }


    Column {
        TopBackTittleHome(painter = painterResource(R.drawable.quaylai),

            placeholder = title ,
            onBackStack = { navHostController.popBackStack() },
            onHomeClick = { viewModel.gotoHome() }
        )
        CustomDividerColor()
        Box(Modifier.size(20.dp))


        if (state.tabs.size > 1) {


            SegmentTabLocal(tabs = state.tabs, selectedTab = state.selectedTab, onTabSelected = {

                viewModel.onCategorySelected(it, state.tabs)
            })
        }

        if ( state.qrCode != null) {

            val imageBitmap = remember(state.qrCode) {
                decodeBase64ToBitmap(state.qrCode)
            }

            if (imageBitmap != null) {
                Image(
                    bitmap =  imageBitmap,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(85.dp).fillMaxSize()
                )
            }
        }
    }
}
