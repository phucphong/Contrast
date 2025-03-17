package com.contrast.Contrast.managers


import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation
import androidx.core.graphics.toColorInt
import com.itechpro.domain.model.ThemeEntity


object ThemeManager {
    private const val DEFAULT_FOREGROUND = "0|#95a5a6,#7f8c8d"
    private const val DEFAULT_BACKGROUND = "1|#2c3e50,#34495e"
    private const val DEFAULT_TEXT = "2|#ffffff,#eeeeee"
    private const val DEFAULT_TINT = "3|#3498db,#2980b9"
    private const val DEFAULT_HIGHLIGHT = "4|#2ecc71,#27ae60"
    private const val DEFAULT_NORMAL = "5|#00000000,#00111111"
    private const val DEFAULT_SELECTED = "6|#1abc9c,#16a085"
    private const val DEFAULT_DISABLED = "7|#95a5a6,#7f8c8d"

    private var theme: ThemeEntity? = null

    var background: ColorEntity? = ColorEntity("background", DEFAULT_BACKGROUND)
        private set
    var foreground: ColorEntity? = ColorEntity("foreground", DEFAULT_FOREGROUND)
        private set
    var tint: ColorEntity? = ColorEntity("tint", DEFAULT_TINT)
        private set
    var text: ColorEntity? = ColorEntity("text", DEFAULT_TEXT)
        private set

    var normal: ColorEntity? = ColorEntity("normal", DEFAULT_NORMAL)
        private set
    var selected: ColorEntity? = ColorEntity("selected", DEFAULT_SELECTED)
        private set
    var highlight: ColorEntity? = ColorEntity("highlight", DEFAULT_HIGHLIGHT)
        private set
    var disabled: ColorEntity? = ColorEntity("disabled", DEFAULT_DISABLED)
        private set

    fun update(theme: ThemeEntity?) {
        this.theme = theme
        foreground = ColorEntity("foreground", theme?.foreground ?: DEFAULT_FOREGROUND)
        background = ColorEntity("background", theme?.background ?: DEFAULT_BACKGROUND)
        text = ColorEntity("text", theme?.text ?: DEFAULT_TEXT)
        tint = ColorEntity("tint", theme?.tint ?: DEFAULT_TINT)
        normal = ColorEntity("normal", theme?.normal ?: DEFAULT_NORMAL)
        highlight = ColorEntity("highlight", theme?.highlight ?: DEFAULT_HIGHLIGHT)
        selected = ColorEntity("selected", theme?.selected ?: DEFAULT_SELECTED)
        disabled = ColorEntity("disabled", theme?.disabled ?: DEFAULT_DISABLED)
    }
}

class ColorEntity(key: String, value: String) {
    companion object {
        private const val TAG = "ColorEntity"
    }

    var orientation: Orientation = Orientation.TOP_BOTTOM
        private set
    private var colors: IntArray = intArrayOf()

    val lastColor: Int get() = colors.first()
    val firstColor: Int get() = colors.last()

    init {
        val values = value.split("|")
        orientation =
            when (values.first()) {
                "0" -> Orientation.TOP_BOTTOM
                "1" -> Orientation.TR_BL
                "2" -> Orientation.LEFT_RIGHT
                "3" -> Orientation.BR_TL
                "4" -> Orientation.BOTTOM_TOP
                "5" -> Orientation.BL_TR
                "6" -> Orientation.LEFT_RIGHT
                "7" -> Orientation.TL_BR
                else -> Orientation.TOP_BOTTOM
            }
        val hexes = values.last().split(",")
        colors = hexes.map { it.toColorInt() }.toIntArray()
    }

    val drawable get() = GradientDrawable(orientation, colors)
}
