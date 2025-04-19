package com.itechpro.domain.model
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Notification(
    val id:  String?="",
    val iddoituong:  String?="",
    val tieude:  String?="",
    val noidung:  String?="",
    val ngaygui:  String?="",
    val loai:  String?="",
    val daxem:  Boolean?=false,

    val type: NotificationType? = null, // Loại thông báo (Voucher, Sự kiện, Tin tức)
    val status: String? = ""
)

enum class NotificationType {
    VOUCHER, EVENT, NEWS
}
