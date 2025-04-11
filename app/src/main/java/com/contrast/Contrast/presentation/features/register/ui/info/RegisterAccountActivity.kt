package com.contrast.Contrast.presentation.features.register.ui.info

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.presentation.features.call.dialer.DialerScreen
import com.contrast.Contrast.presentation.features.call.viewmodel.DialerViewModel
import com.contrast.Contrast.presentation.features.customer.ui.add.CustomerScreen
import com.contrast.Contrast.presentation.features.customer.ui.detail.CustomerDetailScreen
import com.contrast.Contrast.presentation.features.news.list.NewsScreen
import com.contrast.Contrast.presentation.features.news.viewModel.NewsViewModel
import com.contrast.Contrast.presentation.features.video.VideoScreen
import com.contrast.Contrast.presentation.features.video.viewModel.VideoViewModel
import com.contrast.Contrast.presentation.features.work.WorkScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class RegisterAccountActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
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

            val viewModel: NewsViewModel = hiltViewModel()
            NewsScreen(
                viewModel = viewModel,

                )

        }
    }
}
