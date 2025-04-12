package com.contrast.Contrast.presentation.components.youtube

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.contrast.Contrast.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class FullScreenYoutubeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsets.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val videoId = intent.getStringExtra("videoId") ?: run {
            finish()
            return
        }

        val youTubePlayerView = YouTubePlayerView(this).apply {
            enableAutomaticInitialization = false
            initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }, handleNetworkEvents = true)
        }

        // 🔙 Tạo nút back
        val backButton = ImageView(this).apply {
            setImageResource(R.drawable.ic_back) // 👉 dùng icon back của bạn
            setPadding(40, 40, 40, 40)
            setOnClickListener {
                finish() // 🔁 quay lại màn trước
            }
        }

        // 📦 Đặt player + back button vào FrameLayout
        val container = FrameLayout(this).apply {
            addView(youTubePlayerView,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            )

            addView(backButton,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 50
                    leftMargin = 50
                }
            )
        }

        setContentView(container)
        lifecycle.addObserver(youTubePlayerView)
    }
}
