package com.contrast.Contrast.presentation.navigator.navgraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.account.personalInfo.PersonalInfoScreen
import com.contrast.Contrast.presentation.navigator.routers.AccountRoutes


fun NavGraphBuilder.registerAccountRoutes(navController: NavHostController
) {



    composable(AccountRoutes.PersonalInfo.route) {
        PersonalInfoScreen(navController = navController)
    }
}
