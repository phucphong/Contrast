package com.contrast.Contrast.extensions


import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")

    @RequiresApi(Build.VERSION_CODES.O)
    @ToJson
    fun toJson(value: LocalDateTime): String {
        return value.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @FromJson
    fun fromJson(value: String): LocalDateTime {
        return LocalDateTime.parse(value, formatter)
    }
}
