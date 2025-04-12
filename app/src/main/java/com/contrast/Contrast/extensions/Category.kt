package com.contrast.Contrast.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.contrast.Contrast.R
import com.itechpro.domain.model.Category

@Composable
fun Category.label(): String {
    return when (code?.lowercase()) {
        "service" -> stringResource(R.string.service)
        "product" -> stringResource(R.string.product)
        "info" -> stringResource(R.string.info)
        "other" -> stringResource(R.string.other)
        "timeline" -> stringResource(R.string.timeline)
        "exchange" -> stringResource(R.string.exchange)
        else -> name.orEmpty()
    }
}
