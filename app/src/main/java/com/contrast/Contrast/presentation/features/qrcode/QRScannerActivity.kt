package com.contrast.Contrast.presentation.features.qrcode


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class QRScannerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRScannerScreenPreview()
        }
    }
}
