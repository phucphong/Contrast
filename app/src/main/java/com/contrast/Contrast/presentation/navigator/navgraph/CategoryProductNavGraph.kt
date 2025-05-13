package com.contrast.Contrast.presentation.navigator.navgraph



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage

import com.contrast.Contrast.presentation.features.affiliate.home.HomePage


import com.contrast.Contrast.presentation.navigator.router.routes.AffiliateRoutes
import com.contrast.Contrast.presentation.navigator.router.routes.CategoryProductRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerCategoryProductRoutes(navController: NavHostController
) {

    composable(CategoryProductRoutes.CategoryProduct.route) {
        CategoryAffiliatePage(navController,"0")
    }



}
