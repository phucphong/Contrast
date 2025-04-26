package com.contrast.Contrast.presentation.features.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.contrast.Contrast.presentation.features.main.ui.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContrastActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            val  navHostController= NavHostController(this)
//            MediaPickerScreenNew(
//
//                maxCount = 0,
//                allowImage = true,
//                allowVideo = true,
//                compressedFiles = true,
//                onSendClick = { uris ->
//
//                },
//                navHostController = navHostController,
//
//            )

            MainScreen()
//            AffiliateMainScreen()

        }
    }

}
