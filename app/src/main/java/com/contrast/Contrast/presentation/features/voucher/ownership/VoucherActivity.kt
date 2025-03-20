package com.contrast.Contrast.presentation.features.voucher.ownership

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.contrast.Contrast.presentation.features.voucher.detail.VoucherDetailScreen
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class VoucherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoucherDetailScreen(onBackClick = { /* Xử lý back */ })
        }
    }
}
