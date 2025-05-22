package com.contrast.Contrast.presentation.navigator.navgraph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.contrast.Contrast.presentation.features.news.detail.NewDetailScreen

import com.contrast.Contrast.presentation.navigator.routers.NewsRoutes

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerNewsRoutes(navController: NavHostController
) {

    composable(
        route = NewsRoutes.NewsDetail.route,
        arguments = NewsRoutes.NewsDetail.arguments
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""

        NewDetailScreen(navController, id)
    }

}