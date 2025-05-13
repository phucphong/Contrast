package com.contrast.Contrast.presentation.navigator.graph



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.contrast.Contrast.presentation.components.media.MediaPickerScreenNew
import com.contrast.Contrast.presentation.components.media.MediaCreateViewModel

import com.contrast.Contrast.presentation.features.review.ui.AddReviewScreen
import com.contrast.Contrast.presentation.features.review.ui.ReviewsFilterScreen

import com.contrast.Contrast.presentation.navigator.router.NavRoutes
@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.reviewNavGraph(navController: NavHostController) {
    navigation(
        startDestination = NavRoutes.AddReview.route,
        route = NavRoutes.ReviewRoot.route // ✅ route = review_root
    ) {

        composable(
            route = NavRoutes.AddReview.route,
            arguments = NavRoutes.AddReview.arguments
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavRoutes.ReviewRoot.route)
            }
            val sharedViewModel = hiltViewModel<MediaCreateViewModel>(parentEntry)
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val idUnit =backStackEntry.arguments?.getString("idUnit") ?: ""
            val fileTxt = backStackEntry.arguments?.getString("fileTxt") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            AddReviewScreen(
                navHostController = navController,
                id = id,
                idUnit = idUnit,
                fileTxt = fileTxt,
                name = name,
                mediaCreateViewModel = sharedViewModel
            )
        }

        composable(
            route = NavRoutes.MediaPicker.route,
            arguments = NavRoutes.MediaPicker.arguments
        ) { backStackEntry ->
            // ✅ Sửa dùng remember
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(NavRoutes.ReviewRoot.route)
            }

            val sharedViewModel = hiltViewModel<MediaCreateViewModel>(parentEntry)

            val maxCount = backStackEntry.arguments?.getInt("maxCount") ?: 5
            val allowImage = backStackEntry.arguments?.getBoolean("allowImage") ?: true
            val allowVideo = backStackEntry.arguments?.getBoolean("allowVideo") ?: true
            val compressedFiles = backStackEntry.arguments?.getBoolean("compressedFiles") ?: false

            MediaPickerScreenNew(
                navController,
                maxCount = maxCount,
                allowImage = allowImage,
                allowVideo = allowVideo,
                compressedFiles = compressedFiles,
                onSendClick = { uris ->
                    sharedViewModel.addSelectedMedia(uris)
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = NavRoutes.Reviews.route,
            arguments = NavRoutes.Reviews.arguments
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            ReviewsFilterScreen(navController, id)
        }

    }
}
