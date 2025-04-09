package com.contrast.Contrast.extensions

import androidx.compose.ui.graphics.Color
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ColorAdapter {
    @ToJson
    fun toJson(color: Color): String {
        return "#%02x%02x%02x".format(
            (color.red * 255).toInt(),
            (color.green * 255).toInt(),
            (color.blue * 255).toInt()
        )
    }

    @FromJson
    fun fromJson(colorString: String): Color {
        val intColor = android.graphics.Color.parseColor(colorString)
        return Color(intColor)
    }
}
