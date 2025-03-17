package com.contrast.Contrast.presentation.extensions

import com.contrast.Contrast.R
import com.itechpro.domain.model.LanguageEntity


fun LanguageEntity.icon(): Int? {
    return when (code) {
        "ko" -> R.drawable.ic_flag_ko
        "ja" -> R.drawable.ic_flag_ja
        "en" -> R.drawable.ic_flag_us
        "vi" -> R.drawable.ic_flag_vi
        "zh" -> R.drawable.ic_flag_zh
        else -> null
    }
}
