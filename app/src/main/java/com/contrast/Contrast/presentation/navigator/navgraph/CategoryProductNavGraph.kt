package com.contrast.Contrast.presentation.navigator.navgraph



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage

import com.contrast.Contrast.presentation.navigator.routers.CategoryProductRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerCategoryProductRoutes(navController: NavHostController
) {

    composable(CategoryProductRoutes.CategoryProduct.route) {
        CategoryAffiliatePage(navController,"0")
    }



}
