package com.contrast.Contrast.presentation.features.register.ui.info

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliateModel
import com.contrast.Contrast.presentation.features.affiliate.category.CategoryAffiliatePage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class RegisterAccountActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val  navHostController= NavHostController(this)
//            CustomerScreen(navController = navController,"0")

//            WorkScreen()

//            CustomerDetailScreen(
//                ido = "0",
//                onBackClick = { navController.popBackStack() },
//                navController = navController // 🟢 truyền vào đây!
//            )


//            val viewModel: DialerViewModel = hiltViewModel()

//            DialerScreen(
//                viewModel = viewModel,
//                navController = navController
//            )

//            val viewModel: VideoViewModel = hiltViewModel()
//            VideoScreen(
//                viewModel = viewModel,
//
//            )

//            val viewModel: NewsViewModel = hiltViewModel()

//            NewsScreen(
//                viewModel = viewModel,
//
//                )

//            val viewModel: NotificationViewModel = hiltViewModel()
//            NotificationScreen(
//                viewModel = viewModel,
//
//                )

            val viewModel: CategoryAffiliateModel = hiltViewModel()
            CategoryAffiliatePage(navController,
                viewModel = viewModel,
                        categoryId = "0"

                )
//            val viewModel: HomeAffiliateModel = hiltViewModel()
//            HomeAffiliatePage(navHostController,
//                viewModel = viewModel
//
//            )

//            val viewModel: VideoViewModel = hiltViewModel()
//            VideoScreen(
//                viewModel = viewModel,
//
//                )

//  val viewModel: NewsViewModel = hiltViewModel()
//            NewDetailScreen(
//                ido="0",
//                viewModel = viewModel,
//                navController = navController
//                )

//            ProfileScreen()


        }

    }
}
