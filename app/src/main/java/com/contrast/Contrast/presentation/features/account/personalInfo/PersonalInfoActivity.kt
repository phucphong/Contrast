package com.contrast.Contrast.presentation.features.account.personalInfo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // ✅ Bắt buộc nếu Activity cần inject ViewModel
class PersonalInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersonalInfoScreenPreview()
        }
    }
}
