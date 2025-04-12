package com.contrast.Contrast.presentation.components.youtube

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController

@Composable
fun YoutubeVideoPlayerView(
    videoId: String,
    modifier: Modifier = Modifier,
    onRequestFullScreen: (() -> Unit)? = null // <- thêm vào
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        AndroidView(
            factory = {
                YouTubePlayerView(it).apply {
                    enableAutomaticInitialization = false
                    lifecycle.addObserver(this)

                    initialize(
                        object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                // ✅ Tự động load video và chờ play
                                youTubePlayer.cueVideo(videoId, 0f)

                                val controller = DefaultPlayerUiController(this@apply, youTubePlayer).apply {
                                    showFullscreenButton(true)    // ✅ Hiện nút fullscreen
                                    showMenuButton(false)         // ❌ Ẩn nút ⋮
                                }

                                controller.setFullScreenButtonClickListener {
                                    onRequestFullScreen?.invoke()
                                }


                                setCustomPlayerUi(controller.rootView)
                            }
                        },
                        handleNetworkEvents = true
                    )
                }
            },
            modifier = Modifier.matchParentSize()
        )
    }
}
