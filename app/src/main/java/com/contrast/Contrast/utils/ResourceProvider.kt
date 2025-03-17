package com.contrast.Contrast.utils

interface ResourceProvider {
    fun getString(resId: Int): String
}