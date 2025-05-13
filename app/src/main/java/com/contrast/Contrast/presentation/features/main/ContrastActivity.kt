package com.contrast.Contrast.presentation.features.main

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.contrast.Contrast.presentation.navigator.graph.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContrastActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startIntent = intent // 👈 intent chứa ACTION_VIEW hoặc data



        setContent {
            val navController = rememberNavController()
            AppNavGraph(navController, startIntent)
        }
    }

}
