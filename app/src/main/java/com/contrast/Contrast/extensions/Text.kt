package com.contrast.Contrast.extensions

fun splitTextToTwoLines(text: String): String {
    val words = text.trim().split(" ")
    if (words.size <= 1) return text

    val mid = words.size / 2
    val firstLine = words.take(mid).joinToString(" ")
    val secondLine = words.drop(mid).joinToString(" ")
    return "$firstLine\n$secondLine"
}
