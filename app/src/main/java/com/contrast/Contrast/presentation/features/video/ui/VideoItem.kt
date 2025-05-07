package com.contrast.Contrast.presentation.features.video.ui

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.youtube.FullScreenYoutubeActivity

import com.contrast.Contrast.presentation.components.youtube.YoutubeVideoPlayerView

import com.itechpro.domain.model.Video

@Preview(device = Devices.PHONE, showBackground = true)
@Composable
fun VideoItem(
    video: Video,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
    ) {



        val context = LocalContext.current

        YoutubeVideoPlayerView(
            videoId = video.idvideo,
            onRequestFullScreen = {
                context.startActivity(
                    Intent(context, FullScreenYoutubeActivity::class.java).apply {
                        putExtra("videoId", video.idvideo)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // 🔑 quan trọng
                    }
                )
            }
        )


        Spacer(modifier = Modifier.height(6.dp))

        CustomText(
            text = video.ten,
            fontSize = 14.sp,

            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)

        )
    }
}
