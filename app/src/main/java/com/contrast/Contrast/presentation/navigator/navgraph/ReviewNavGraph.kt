package com.contrast.Contrast.presentation.navigator.navgraph


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.contrast.Contrast.presentation.components.media.MediaPickerScreenNew
import com.contrast.Contrast.presentation.components.media.MediaCreateViewModel
import com.contrast.Contrast.presentation.features.review.ui.AddReviewScreen
import com.contrast.Contrast.presentation.features.review.ui.ReviewsFilterScreen
import com.contrast.Contrast.presentation.navigator.router.routes.ReviewRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerReviewRoutes(navController: NavHostController) {

    composable(
        route = ReviewRoutes.AddReview.route,
        arguments = ReviewRoutes.AddReview.arguments
    ) { backStackEntry ->
        val sharedViewModel = hiltViewModel<MediaCreateViewModel>(backStackEntry)
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val idUnit = backStackEntry.arguments?.getString("idUnit") ?: ""
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
        route = ReviewRoutes.MediaPicker.route,
        arguments = ReviewRoutes.MediaPicker.arguments
    ) { backStackEntry ->
        val parentRoute = backStackEntry.arguments?.getString("parentRoute")
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(parentRoute!!)
        }
        val sharedViewModel = hiltViewModel<MediaCreateViewModel>(parentEntry!!)

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
        route = ReviewRoutes.Reviews.route, // = "product_Reviews/{id}"
        arguments = ReviewRoutes.Reviews.arguments
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        ReviewsFilterScreen(navController, id)
    }

}
