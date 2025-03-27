package com.contrast.Contrast.presentation.features.forgotPassword

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class ForgotPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewForgotPasswordScreen()
        }
    }
}
