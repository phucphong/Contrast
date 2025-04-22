package com.contrast.Contrast.utils


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkChecker @Inject constructor() {

    suspend fun isInternetAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val address = InetAddress.getByName("google.com")
                !address.equals("")
            } catch (e: Exception) {
                false
            }
        }
    }
}
