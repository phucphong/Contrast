package com.itechpro.domain.model.evaluate



import android.net.Uri
import com.squareup.moshi.JsonClass

import com.itechpro.domain.model.product.AttachFile


@JsonClass(generateAdapter = true)
data class EvaluateInput(

    val rating: Int = 0,
    val comment: String = "",
    val imageUris: List<Uri> = emptyList(),
    val videoUri: Uri? = null

    )
