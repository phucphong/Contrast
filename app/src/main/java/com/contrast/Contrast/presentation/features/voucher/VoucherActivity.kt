package com.contrast.Contrast.presentation.features.voucher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.qrcode.QRScannerScreenPreview
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class VoucherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoucherScreen()
        }
    }
}
