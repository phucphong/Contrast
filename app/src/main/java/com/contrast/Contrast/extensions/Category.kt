package com.contrast.Contrast.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.contrast.Contrast.R
import com.itechpro.domain.model.category.Category

@Composable
fun Category.label(): String {
    return when (code?.lowercase()) {
        "dichvu" -> stringResource(R.string.service)
        "huuhinh" -> stringResource(R.string.product)
        "info" -> stringResource(R.string.info)
        "other" -> stringResource(R.string.other)
        "timeline" -> stringResource(R.string.timeline)
        "exchange" -> stringResource(R.string.exchange)
        "payment_info" -> stringResource(R.string.paymentInfo)
        "qrcode" -> stringResource(R.string.qrcode)
        "all" -> stringResource(R.string.all)
        "payment_waiting" -> stringResource(R.string.payment_waiting)
        "paymented" -> stringResource(R.string.paymented)
        "personal" -> stringResource(R.string.personal)
        "ios" -> stringResource(R.string.ios)
        "android" -> stringResource(R.string.android)
        "original" -> stringResource(R.string.original)
        "discount" -> stringResource(R.string.discount)
        "contact" -> stringResource(R.string.contact)
        "product" -> stringResource(R.string.product)
        "attach" -> stringResource(R.string.attach)
        "email" -> stringResource(R.string.email)
        "call" -> stringResource(R.string.call)
        "sms" -> stringResource(R.string.sms)
        "zalo" -> stringResource(R.string.zalo)
        "facebook" -> stringResource(R.string.facebook)
        else -> name.orEmpty()
    }
}
