package com.contrast.Contrast.presentation.features.splas

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.login.ui.LoginActivity
import com.contrast.Contrast.presentation.features.main.ContrastActivity
import com.contrast.Contrast.presentation.features.store.detail.StoreDetailActivity
import com.contrast.Contrast.presentation.features.register.ui.info.RegisterAccountActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launchWhenStarted {
            delay(1500) // delay splash 1.5s
            viewModel.checkLoginState()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.navigateTo.collect { clazz ->
                clazz?.let {
                    startActivity(Intent(this@SplashActivity, it))
                    finish()
                }
            }
        }
    }
}
