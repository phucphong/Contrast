package com.contrast.Contrast.presentation.mapper



import com.contrast.Contrast.R
import com.itechpro.domain.model.category.Category

/**
 * Map mã category sang resource icon tương ứng.
 * Chỉ nên sử dụng ở tầng UI (presentation), không đưa xuống domain/data.
 */
fun Category.mapIcon(): Int? {
    return when (this.ma) {
        "donhangchoxacnhan" -> R.drawable.oderwaiting
        "donhangdaxacnhan" -> R.drawable.booking
        "donhang" -> R.drawable.purchaseorder

        "cupons" -> R.drawable.coupon
        "setting" -> R.drawable.settings
        "sanphamdaxem" -> R.drawable.clock_aff
        "sanphamdaluu" -> R.drawable.heart_aff

        "hangthe", "lieutrinhdangthuchien" -> R.drawable.process
        "lichthuchiendichvu" -> R.drawable.appointmentbook
        "phantramchietkhau" -> R.drawable.discount
        "lichsuthanhtoan" -> R.drawable.history
        "huongdanspatainha" -> R.drawable.manual

        "dailycapduoi" -> R.drawable.hierarchy
        "trothanhdaily", "nangcapthanhdaily" -> R.drawable.agency
        "cantuvan" -> R.drawable.opportunity
        "doanhsotieudungcanhan" -> R.drawable.consumer
        "baocaodoanhsotheotungdaily" -> R.drawable.topthree

        "baocaohoahongthudong" -> R.drawable.levelup
        "baocaothuongthangcap", "baocaothuongthangcapcanhan" -> R.drawable.progressreport

        // Các loại coaching/video/sở thích
        "khoahoccuatoi", "coachinh121", "lichcoaching", "hoidap",
        "sanphamquantam", "banquantam", "khoahocdadangky",
        "kehoachcoaching", "videoyeuthich", "tiendokehoachcoaching",
        "thanhtoankhachhang", "thoigianthanhtoan", "baocaothanhtoankh",
        "doimatkhau", "switchApp" -> R.drawable.usertranfer

        "qrcodegiothieu" -> R.drawable.qrcodegiothieu
        "thunhap" -> R.drawable.commission
        "tiepthilienket" -> R.drawable.governance

        else -> null
    }
}

/**
 * Gán icon UI cho danh sách Category (mutable).
 */
fun List<Category>.withUiIcon(): List<Category> {
    forEach { it.icon = it.mapIcon() }
    return this
}
