package com.contrast.Contrast.presentation.components.youtube

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubeFullScreenView(videoId: String) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    AndroidView(
        factory = {
            YouTubePlayerView(it).apply {
                enableAutomaticInitialization = false
                lifecycle.addObserver(this)

                initialize(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(videoId, 0f) // autoplay
                        }
                    },
                    handleNetworkEvents = true
                )
            }
        },
        modifier = Modifier
            .fillMaxSize()
    )
}
