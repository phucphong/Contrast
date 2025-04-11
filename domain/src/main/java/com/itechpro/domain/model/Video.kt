package com.itechpro.domain.model

data class Video(
    val title: String,
    val videoId: String,
    val thumbnailUrl: String,
    val avatarUrl: String,
    val channelName: String,
    val showHeart: Boolean? = false,
    val showAdd: Boolean? = false,

)
