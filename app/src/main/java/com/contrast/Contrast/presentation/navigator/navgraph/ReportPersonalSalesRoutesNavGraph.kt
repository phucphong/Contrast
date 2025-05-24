package com.contrast.Contrast.presentation.navigator.navgraph




import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.report.personal_sales.PersonalSalesScreen
import com.contrast.Contrast.presentation.features.report.report_passive_commission.ReportPassiveCommissionScreen
import com.contrast.Contrast.presentation.features.report.report_sales_Agency.ReportSalesByAgencyScreen
import com.contrast.Contrast.presentation.features.report.report_upto_level.ReportUpToLevelScreen
import com.contrast.Contrast.presentation.navigator.routers.ReportPersonalSalesRoutes


@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerReportPersonalSalesRoutes(navController: NavHostController) {


    composable(
        route = ReportPersonalSalesRoutes.PersonalConsumptionSales.route,
        arguments = ReportPersonalSalesRoutes.PersonalConsumptionSales.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        PersonalSalesScreen(navController,title )
    }

    composable(
        route = ReportPersonalSalesRoutes.SalesReportByAgency.route,
        arguments = ReportPersonalSalesRoutes.SalesReportByAgency.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ReportSalesByAgencyScreen(navController,title)
    }


    composable(
        route = ReportPersonalSalesRoutes.PassiveCommissionReport.route,
        arguments = ReportPersonalSalesRoutes.PassiveCommissionReport.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        ReportPassiveCommissionScreen(navController,title)
    }

    composable(
        route = ReportPersonalSalesRoutes.UpToLevelSales.route,
        arguments = ReportPersonalSalesRoutes.UpToLevelSales.arguments
    ) { backStackEntry ->
        val title = backStackEntry.arguments?.getString("title") ?:""
        val type = backStackEntry.arguments?.getString("type") ?:""
        ReportUpToLevelScreen(navController,type,title)
    }



}
