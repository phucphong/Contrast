package com.contrast.Contrast.presentation.navigator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.contrast.Contrast.presentation.navigator.graph.accountNavGraph
import com.contrast.Contrast.presentation.navigator.graph.affiliateNavGraph
import com.contrast.Contrast.presentation.navigator.graph.contrastNavGraph
import com.contrast.Contrast.presentation.navigator.graph.reviewNavGraph


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController,
               idProductFromShare: String? = null,
               idUnitFromShare: String? = null,
               introducerId: String? = null,
               ) {

    NavHost(navController, startDestination = NavRoutes.AffiliateRoot.route) {
        affiliateNavGraph(navController,
            idProductFromShare = idProductFromShare,
            idUnitFromShare = idUnitFromShare,
        introducerId = introducerId)
        contrastNavGraph(navController)
        reviewNavGraph(navController)// dùng chung viewModel để lấy dữ liệu ảnh

    }
}
