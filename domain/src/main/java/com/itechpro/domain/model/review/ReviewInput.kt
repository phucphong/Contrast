package com.itechpro.domain.model.review



import android.net.Uri
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ReviewInput(

    val rating: Int = 0,
    val comment: String = "",
    val imageUris: List<Uri> = emptyList(),
    val videoUri: Uri? = null

    )
