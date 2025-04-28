package com.contrast.Contrast.presentation.features.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.affiliate.AffiliateActivity
import com.contrast.Contrast.presentation.features.login.ui.LoginActivity
import com.contrast.Contrast.presentation.features.main.ContrastActivity
import com.contrast.Contrast.presentation.features.store.detail.StoreDetailActivity
import com.contrast.Contrast.presentation.features.register.ui.info.RegisterAccountActivity
import com.contrast.Contrast.presentation.features.splas.SplashNavigation
import com.contrast.Contrast.presentation.features.splas.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            delay(1500) // Delay Splash 1.5s

            if (intent?.action == Intent.ACTION_VIEW) {
                viewModel.handleShareIntent(intent)
            } else {
                viewModel.checkLoginState()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.navigation.collectLatest { event ->

                Log.e("event",event.toString())
                when (event) {
                    is SplashNavigation.GoToLogin -> {
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                        finish()
                    }
                    is SplashNavigation.GoToMain -> {
                        startActivity(Intent(this@SplashActivity, ContrastActivity::class.java))
                        finish()
                    }
                    is SplashNavigation.ShowAffiliateInfo -> {
                        // thoong báo thay đổi domain  ok cancel

                    }
                    is SplashNavigation.OpenAffiliatePage -> {
                        val intent = Intent(this@SplashActivity, AffiliateActivity::class.java).apply {
                            putExtra("id", event.id)
                            putExtra("idUnit", event.idUnit)
                            putExtra("introducerId", event.introducerId)
                        }
                        startActivity(intent)
                        finish()
                    }
                    null -> {
                        // Không làm gì
                    }
                }
            }
        }
    }
}
