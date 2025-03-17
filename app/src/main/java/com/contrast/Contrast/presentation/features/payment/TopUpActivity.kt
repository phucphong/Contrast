package com.contrast.Contrast.presentation.features.payment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.contrast.Contrast.presentation.features.payment.ui.AmountInputScreen
import com.contrast.Contrast.presentation.features.payment.ui.PreviewTopUpScreen

class TopUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewTopUpScreen()
        }
    }
}
