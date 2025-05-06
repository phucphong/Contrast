package com.itechpro.domain.model.review



import com.itechpro.domain.model.FileUpload
import okhttp3.MultipartBody

data class ReviewAttach(
    val description: String? = null,     // Mô tả thêm
    val ido: String? = null,              // ID người dùng (nếu có)
    val noidung: String? = null,          // Nội dung đánh giá
    val diem: String? = null,              // Số điểm đánh giá (1-5 sao)
    val idsanpham: String? = null,         // ID sản phẩm
    val iddonvi: String? = null,            // ID đơn vị (cửa hàng)
    val loaitk: String? = null,       // Loại tài khoản (ví dụ khách hàng / admin)
    val idsfilexoa: String? = null,       // Loại tài khoản (ví dụ khách hàng / admin)
    val rank: String? = null,       // Loại tài khoản (ví dụ khách hàng / admin)
    val mamenu: String? = null,             // Mã menu (nếu có)
    val hanhdong: String? = null,           // Hành động (thêm/sửa)
    val device: String? = null,            // Tên thiết bị
    val os: String? = null,                // Tên hệ điều hành
    val files: List<MultipartBody.Part> ?= emptyList()           // Danh sách File đính kèm (ảnh/video)
)
