package com.contrast.Contrast.presentation.features.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.contrast.Contrast.presentation.features.login.ui.LoginActivity
import com.contrast.Contrast.presentation.features.register.ui.phone.RegisterPhoneActivity

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WelcomeScreen(
                onLoginClick = {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                },
                onRegisterClick = {
                    val intent = Intent(this, RegisterPhoneActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}