package com.contrast.Contrast.extensions

fun String.capitalizeEachWord(): String {
    return split(" ").joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}