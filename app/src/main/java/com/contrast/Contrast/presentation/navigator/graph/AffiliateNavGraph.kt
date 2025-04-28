package com.contrast.Contrast.presentation.navigator.graph


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.contrast.Contrast.presentation.components.media.MediaPickerScreenNew
import com.contrast.Contrast.presentation.components.media.MediaCreateViewModel
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.features.affiliate.home.HomePage
import com.contrast.Contrast.presentation.features.cart.ui.CartScreen

import com.contrast.Contrast.presentation.features.notification.ui.NotificationScreen


import com.contrast.Contrast.presentation.features.video.VideoScreen
import com.contrast.Contrast.presentation.navigator.NavRoutes

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.affiliateNavGraph(navController: NavHostController,
                                      idProductFromShare: String? = null,
                                      idUnitFromShare: String? = null,
                                      introducerId: String? = null,
                                      ) {
    navigation(
        startDestination = NavRoutes.AffiliateHome.route, route = NavRoutes.AffiliateRoot.route
    ) {
        composable(NavRoutes.AffiliateHome.route) {
            HomePage(idProductFromShare, idUnitFromShare,introducerId,navController)
        }
        composable(NavRoutes.Video.route) {
            VideoScreen(navController)
        }
        composable(NavRoutes.Notifications.route) {
            NotificationScreen(navController)
        }
        composable(NavRoutes.Carts.route) {
            CartScreen(navController)
        }

        productnavGraph(navController)

        accountNavGraph(navController)




    }
}

