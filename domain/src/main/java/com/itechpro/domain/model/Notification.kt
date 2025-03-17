package com.itechpro.domain.model

data class Notification(
    val id: Int,
    val title: String,
    val message: String?,
    val date: String,
    val type: NotificationType? = null, // Loại thông báo (Voucher, Sự kiện, Tin tức)
    val status: String? = null // Trạng thái đơn hàng (nếu có)
)

enum class NotificationType {
    VOUCHER, EVENT, NEWS
}

fun sampleNotifications() = listOf(
    Notification(1, "Đơn của bạn đang được chuẩn bị", null, "Vừa xong", status = "Vừa xong"),
    Notification(2, "Bạn đã nhận được 1 Voucher ưu đãi 30K", "Chào mừng thành viên mới. Contrast xin gửi tặng bạn một Voucher", "3 ngày", NotificationType.VOUCHER),
    Notification(3, "Mừng ngày khai giảng 5-9", "Giảm ngay 30K khi mua đồ uống bất kỳ dành cho thành viên mới", "3 ngày", NotificationType.EVENT),
    Notification(4, "Contrast Farm chính thức đi vào hoạt động", "Contrast mong muốn mang tới những sản phẩm cafe hữu cơ xứng tầm thế giới...", "3 ngày", NotificationType.NEWS)
)