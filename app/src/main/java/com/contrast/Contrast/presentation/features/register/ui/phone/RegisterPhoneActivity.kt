package com.contrast.Contrast.presentation.features.register.ui.phone

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.contrast.Contrast.presentation.features.auth.RegisterScreen


class RegisterPhoneActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen(
                onBackPress = { finish() }, // Đóng Activity khi nhấn Back
                onConfirm = { phoneNumber ->
                    Toast.makeText(this, "Số điện thoại: $phoneNumber", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
