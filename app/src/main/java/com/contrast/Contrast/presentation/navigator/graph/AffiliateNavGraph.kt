package com.contrast.Contrast.presentation.navigator.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.contrast.Contrast.presentation.components.image.MediaPickerScreen
import com.contrast.Contrast.presentation.components.image.ReviewCreateViewModel
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import com.contrast.Contrast.presentation.features.affiliate.home.HomePage
import com.contrast.Contrast.presentation.features.evaluate.ui.AddReviewScreen
import com.contrast.Contrast.presentation.features.evaluate.ui.EvaluatesScreen
import com.contrast.Contrast.presentation.features.notification.ui.NotificationScreen
import com.contrast.Contrast.presentation.features.product.detail.ProductDetailScreen
import com.contrast.Contrast.presentation.features.video.VideoScreen
import com.contrast.Contrast.presentation.navigator.NavRoutes

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.affiliateNavGraph(navController: NavHostController) {
    navigation(
        startDestination = NavRoutes.AffiliateHome.route,
        route = NavRoutes.AffiliateRoot.route
    ) {
        composable(NavRoutes.AffiliateHome.route) {
            HomePage(navController)
        }
        composable(NavRoutes.Video.route) {
            VideoScreen(navController)
        }


  composable(NavRoutes.Notifications.route) {
            NotificationScreen(navController)
        }
  composable(NavRoutes.NotificationDetail.route) {
            NotificationScreen(navController)
        }

       
        composable(
            route = NavRoutes.ProductDetail.fullRoute,
            arguments = NavRoutes.ProductDetail.arguments
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val idUnit = backStackEntry.arguments?.getString("idUnit") ?: ""
            ProductDetailScreen(navController, id, idUnit)
        }

        composable(
            route = NavRoutes.Evaluates.fullRoute,
            arguments = NavRoutes.Evaluates.arguments
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""

            EvaluatesScreen(navController, id)
        }

        composable(
            route = NavRoutes.AddEvaluate.route,
            arguments = NavRoutes.AddEvaluate.arguments
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val sharedViewModel = hiltViewModel<ReviewCreateViewModel>()

            AddReviewScreen(
                 navController,
                id = id,
                reviewCreateViewModel = sharedViewModel
            )
        }


        composable(
            route = NavRoutes.MediaPicker.route,
            arguments = NavRoutes.MediaPicker.arguments
        ) { backStackEntry ->
            val sharedViewModel = hiltViewModel<ReviewCreateViewModel>()
            val maxCount = backStackEntry.arguments?.getInt("maxCount") ?: 5
            val allowImage = backStackEntry.arguments?.getBoolean("allowImage") ?: true
            val allowVideo = backStackEntry.arguments?.getBoolean("allowVideo") ?: true

            MediaPickerScreen(
                maxCount = maxCount,
                allowImage = allowImage,
                allowVideo = allowVideo,
                onSendClick = { uris ->
                    sharedViewModel.setSelectedMedia(uris)
                    navController.popBackStack()
                }
            )
        }



        composable(NavRoutes.ProductByCategory.route) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: ""
            CategoryAffiliatePage(navController, categoryId)
        }

        composable(NavRoutes.AddServiceRequest.route) { backStackEntry ->
            val idService = backStackEntry.arguments?.getString("idService") ?: ""
            CategoryAffiliatePage(navController, idService)
        }
       composable(NavRoutes.AddServiceRequest.route) { backStackEntry ->
            val idService = backStackEntry.arguments?.getString("idService") ?: ""
            CategoryAffiliatePage(navController, idService)
        }



    }
}

