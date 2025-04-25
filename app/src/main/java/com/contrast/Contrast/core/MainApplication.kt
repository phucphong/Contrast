package com.contrast.Contrast.core

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkRequest
import com.contrast.Contrast.presentation.base.NetworkChangeReceiver
import com.contrast.Contrast.utils.NetworkMonitor
import dagger.hilt.android.HiltAndroidApp



import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.VideoFrameDecoder
import coil.disk.DiskCache
import coil.util.CoilUtils
import okhttp3.OkHttpClient
import java.io.File
@HiltAndroidApp
class MainApplication : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(VideoFrameDecoder.Factory()) // ✅ Cho phép hiển thị thumbnail video
            }
            .crossfade(true)
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .build()
            }
            .build()
    }
}
