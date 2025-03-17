package com.contrast.Contrast.presentation.features.register.ui.otp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


class OTPActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OtpVerificationScreen(
                phoneNumber = "091****678", // Số điện thoại ẩn đi
                onBackPress = { finish() }, // Đóng Activity khi nhấn Back
                onOtpSubmit = { otpCode ->
                    Toast.makeText(this, "Mã OTP: $otpCode", Toast.LENGTH_SHORT).show()
                },
                onResendOtp = {
                    Toast.makeText(this, "Mã OTP đã được gửi lại", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}


