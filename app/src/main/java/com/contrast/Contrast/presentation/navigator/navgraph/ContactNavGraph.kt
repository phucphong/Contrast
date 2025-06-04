package com.contrast.Contrast.presentation.navigator.navgraph


import android.os.Build
import androidx.annotation.RequiresApi

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.contrast.Contrast.presentation.features.contact.detail.ContactDetailScreen
import com.contrast.Contrast.presentation.features.contact.list.ContactOpportunityProjectScreen

import com.contrast.Contrast.presentation.navigator.routers.ContactRoutes

import com.contrast.Contrast.presentation.navigator.routers.OpportunityRoutes



@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerContactRoutes(navController: NavHostController) {


    composable(
        route = ContactRoutes.ContactOpportunityProject.route, // = "product_Reviews/{id}"
        arguments = ContactRoutes.ContactOpportunityProject.arguments
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val type = backStackEntry.arguments?.getString("type") ?: ""

        ContactOpportunityProjectScreen(navController, id,type)
    }
    composable(
        route = ContactRoutes.ContactDetail.route, // = "product_Reviews/{id}"
        arguments = ContactRoutes.ContactDetail.arguments
    ) { backStackEntry ->
        val id = backStackEntry.arguments?.getString("id") ?: ""
        val customer = backStackEntry.arguments?.getString("customer") ?: ""
        val customerEdit = backStackEntry.arguments?.getString("customerEdit") ?: ""

        ContactDetailScreen(navController, id,customer,customerEdit)
    }

}
