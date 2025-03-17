package com.contrast.Contrast.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LanguageUtils {

    // Hàm thay đổi ngôn ngữ của ứng dụng
    fun changeLanguage(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)  // Cập nhật locale

        // Trả về context với cấu hình mới
        return context.createConfigurationContext(config)
    }

    // Hàm để lưu ngôn ngữ đã chọn trong SharedPreferences (tùy chọn)
    fun saveLanguagePreference(context: Context, languageCode: String) {
        val preferences = context.getSharedPreferences("language_pref", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("selected_language", languageCode)
        editor.apply()
    }

    // Hàm để lấy ngôn ngữ đã lưu trong SharedPreferences (tùy chọn)
    fun getSavedLanguage(context: Context): String {
        val preferences = context.getSharedPreferences("language_pref", Context.MODE_PRIVATE)
        return preferences.getString("selected_language", Locale.getDefault().language) ?: Locale.getDefault().language
    }
}