package com.contrast.Contrast.presentation.features.main.store.detail

import android.os.Bundle



import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.contrast.Contrast.presentation.features.voucher.detail.VoucherDetailScreen
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class StoreDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreDetailScreen()
        }
    }
}
