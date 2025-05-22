package com.contrast.Contrast.presentation.navigator.navgraph


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.in_come.InComeScreen
import com.contrast.Contrast.presentation.features.login.ui.LoginScreen
import com.contrast.Contrast.presentation.features.order.list.OrdersScreen
import com.contrast.Contrast.presentation.features.product_view_save.ProductViewSaveScreen

import com.contrast.Contrast.presentation.features.share.ShareProductPage
import com.contrast.Contrast.presentation.features.shareQrcode.ShareQrcodeScreen
import com.contrast.Contrast.presentation.navigator.routers.AuthRoutes

import com.contrast.Contrast.presentation.navigator.routers.ProfileRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerProfileRoutes(
    navController: NavHostController
) {



    composable(
        route = ProfileRoutes.ShareQrcode.route,
        arguments = ProfileRoutes.ShareQrcode.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareQrcodeScreen(navController,title)
    }


    composable(
        route = ProfileRoutes.ShareProduct.route,
        arguments = ProfileRoutes.ShareProduct.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareProductPage(navController,title)
    }

    composable(
        route = ProfileRoutes.InCome.route,
        arguments = ProfileRoutes.InCome.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        InComeScreen(navController,title)
    }
    composable(
        route = ProfileRoutes.ServiceProgress.route,
        arguments = ProfileRoutes.ServiceProgress.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareProductPage(navController,title)
    }

    composable(
        route = ProfileRoutes.ServiceCalendar.route,
        arguments = ProfileRoutes.ServiceCalendar.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareProductPage(navController,title)
    }

    composable(
        route = ProfileRoutes.SpaAtHome.route,
        arguments = ProfileRoutes.SpaAtHome.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareProductPage(navController,title)
    }
    composable(
        route = ProfileRoutes.Agencys.route,
        arguments = ProfileRoutes.Agencys.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareProductPage(navController,title)
    }

    composable(
        route = ProfileRoutes.Opportunity.route,
        arguments = ProfileRoutes.Opportunity.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareProductPage(navController,title)
    }
    composable(
        route = ProfileRoutes.PersonalConsumptionSales.route,
        arguments = ProfileRoutes.PersonalConsumptionSales.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareProductPage(navController,title)
    }

    composable(
        route = ProfileRoutes.PassiveCommissionReport.route,
        arguments = ProfileRoutes.PassiveCommissionReport.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareProductPage(navController,title)
    }

    composable(
        route = ProfileRoutes.SalesReportByAgency.route,
        arguments = ProfileRoutes.SalesReportByAgency.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareProductPage(navController,title)
    }

  composable(
        route = ProfileRoutes.ProductViewSave.route,
        arguments = ProfileRoutes.ProductViewSave.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ShareProductPage(navController,title)
    }




    composable(
        route = ProfileRoutes.ProductViewSave.route,
        arguments = ProfileRoutes.ProductViewSave.arguments
    ) { backStackEntry ->
        val type = backStackEntry.arguments?.getString("type") ?:""
        val title = backStackEntry.arguments?.getString("title") ?:""
        ProductViewSaveScreen(navController,type,title)
    }

    composable(
        route = ProfileRoutes.OderType.route,
        arguments = ProfileRoutes.OderType.arguments
    ) { backStackEntry ->
        val type = backStackEntry.arguments?.getString("type") ?:""
        val title = backStackEntry.arguments?.getString("title") ?:""
        OrdersScreen(navController,type,title)
    }

        composable(
        route = ProfileRoutes.RankAdvancementBonusReport.route,
        arguments = ProfileRoutes.RankAdvancementBonusReport.arguments
    ) { backStackEntry ->
        val type = backStackEntry.arguments?.getString("type") ?:""
        val title = backStackEntry.arguments?.getString("title") ?:""
        ProductViewSaveScreen(navController,type,title)
    }



}