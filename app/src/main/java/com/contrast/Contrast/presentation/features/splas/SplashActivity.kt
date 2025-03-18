package com.contrast.Contrast.presentation.features.splas

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.features.payment.transaction.ui.ConfirmTransactionActivity
import com.contrast.Contrast.presentation.features.voucher.VoucherActivity


class SplashActivity : AppCompatActivity() {

    private  val  token:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val handler = Handler()
        handler.postDelayed({
            startApp()
        }, 2000)
    }

    private fun startactivity(aClass: Class<*>) {
        val i = Intent(this@SplashActivity, aClass)
        startActivity(i)
        finish()
    }

    private fun startApp() {
//       if (token.isEmpty()) {
//            startactivity(NotificationActivity::class.java)
//        } else if (!TextUtils.isEmpty(token)) {
//           startactivity(ContrastActivity::class.java)
//        }
        startactivity(VoucherActivity::class.java)
    }
}