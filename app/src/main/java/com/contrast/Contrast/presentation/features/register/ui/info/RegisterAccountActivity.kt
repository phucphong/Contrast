package com.contrast.Contrast.presentation.features.register.ui.info

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.contrast.Contrast.presentation.features.account.personalInfo.PersonalInfoScreenPreview
import com.contrast.Contrast.presentation.features.membership.MembershipPolicyScreenPreview
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class RegisterAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterAccountScreenPreview()
        }
    }
}
