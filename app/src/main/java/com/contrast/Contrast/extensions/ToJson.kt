package com.contrast.Contrast.extensions

import com.squareup.moshi.Moshi

inline fun <reified T> T.toJson(moshi: Moshi = Moshi.Builder().build()): String {
    return moshi.adapter(T::class.java).toJson(this)
}
