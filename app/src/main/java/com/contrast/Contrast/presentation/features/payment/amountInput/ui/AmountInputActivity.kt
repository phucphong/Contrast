package com.contrast.Contrast.presentation.features.payment.amountInput.ui


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class AmountInputActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AmountInputScreen(
                onClose = { finish() }, // Đóng Activity khi bấm nút X
                onConfirm = { amount ->
                    val resultIntent = Intent().apply {
                        putExtra("entered_amount", amount)
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish() // Đóng Activity và trả kết quả về Activity trước
                }
            )
        }
    }
}
