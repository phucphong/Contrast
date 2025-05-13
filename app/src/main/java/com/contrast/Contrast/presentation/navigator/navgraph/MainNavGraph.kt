package com.contrast.Contrast.presentation.navigator.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.components.media.MediaCreateViewModel
import com.contrast.Contrast.presentation.components.profile.ProfileScreen
import com.contrast.Contrast.presentation.features.affiliate.AffiliateMainScreen
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.features.affiliate.home.HomePage
import com.contrast.Contrast.presentation.features.news.detail.NewDetailScreen
import com.contrast.Contrast.presentation.features.news.list.NewsScreen
import com.contrast.Contrast.presentation.features.review.ui.AddReviewScreen
import com.contrast.Contrast.presentation.features.video.VideoScreen
import com.contrast.Contrast.presentation.navigator.router.routes.AccountRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.AffiliateRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.CategoryProductRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.MainRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.NewsRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.ReviewRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.VideoRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerMainRoutes(navController: NavHostController
) {



    composable(
        route = MainRoutes.Main.route,
        arguments = MainRoutes.Main.arguments
    ) { backStackEntry ->

        val id = backStackEntry.arguments?.getString("id") ?: ""
        val idUnit = backStackEntry.arguments?.getString("idUnit") ?: ""
        val introducerId = backStackEntry.arguments?.getString("introducerId") ?: ""
        AffiliateMainScreen(navController, id, idUnit,introducerId)
    }


}