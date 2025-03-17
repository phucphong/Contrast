package com.contrast.Contrast.utils

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context // ✅ Inject context từ Hilt
) : StringProvider {
    override fun getString(@StringRes resId: Int): String {
        return context.getString(resId) // ✅ Lấy String từ Resource
    }
}
