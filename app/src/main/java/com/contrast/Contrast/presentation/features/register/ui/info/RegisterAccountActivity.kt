package com.contrast.Contrast.presentation.features.register.ui.info

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.presentation.features.customer.ui.add.CustomerScreen
import com.contrast.Contrast.presentation.features.work.WorkScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class RegisterAccountActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CustomerScreen(navController = navController,"0")

//            WorkScreen()
        }
    }
}
