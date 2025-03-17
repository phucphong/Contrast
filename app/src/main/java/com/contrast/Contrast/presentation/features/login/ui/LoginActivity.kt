package com.contrast.Contrast.presentation.features.login.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.contrast.Contrast.presentation.features.main.OnboardingScreen
import com.contrast.Contrast.presentation.features.main.ui.MainScreen

//
//class LoginActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_login)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

//            var showOnboarding by remember { mutableStateOf(true) }
//
//            if (showOnboarding) {
//                OnboardingScreen { showOnboarding = false }
//            } else {
//                LoginScreenPreview()
//            }

            LoginScreenPreview()

        }
    }

}
