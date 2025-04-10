package com.contrast.Contrast.presentation.features.login.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.contrast.Contrast.presentation.features.main.ui.MainScreen


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

//            var showOnboarding by remember { mutableStateOf(true) }
//
//            if (showOnboarding) {
//                OnboardingScreen { showOnboarding = false }
//            } else {
//                LoginScreenPreview()
//            }

            LoginScreenPreview()

        }
    }

}
