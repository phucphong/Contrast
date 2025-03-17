package com.contrast.Contrast.presentation.features.account.forgotPassword

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.register.ui.info.RegisterAccountScreenPreview
import com.contrast.Contrast.presentation.features.register.ui.otp.OtpVerificationScreen
import dagger.hilt.android.AndroidEntryPoint
import com.contrast.Contrast.presentation.features.account.forgotPassword.ForgotPasswordScreen as ForgotPasswordScreen


@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class ForgotPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewForgotPasswordScreen()
        }
    }
}
