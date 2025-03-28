package com.contrast.Contrast.extensions

fun String.capitalizeAfterSpace(): String {
    return this.lowercase().split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.uppercase() }
    }
}
