package com.contrast.Contrast.presentation.navigator.navgraph


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.components.media.MediaPickerScreenNew
import com.contrast.Contrast.presentation.components.media.MediaCreateViewModel
import com.contrast.Contrast.presentation.features.order.detail.OrderDetailScreen
import com.contrast.Contrast.presentation.features.review.ui.AddReviewScreen
import com.contrast.Contrast.presentation.features.review.ui.ReviewsFilterScreen
import com.contrast.Contrast.presentation.navigator.routers.OrderRoutes
import com.contrast.Contrast.presentation.navigator.routers.ReviewRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerOrderRoutes(navController: NavHostController) {




    composable(
        route = OrderRoutes.OderDetail.route, // = "product_Reviews/{id}"
        arguments = OrderRoutes.OderDetail.arguments
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val type = backStackEntry.arguments?.getString("type") ?: ""
        OrderDetailScreen(navController, id,type)
    }

}
