package com.itechpro.domain.model.opportunity




data class Opportunity(
    val pos: Int? = 0,
    val id: String? = "",
    val ma: String? = "",
    val makhachhang: String? = "",
    val ten: String? = "",
    val hinhanhtxt: String? = "",
    val ngaytaotxt: String? = "",
    val ngaytao: String? = "",
    val ngaynhacnho: String? = "",
    val diachigiaohang: String? = "",
    val filetxt: String? = "",
    val trangthai: String? = "",
    val tenkhachhang: String? = "",
    val idkhachhang: String? = "",
    val loaikhachhang: String? = "",
    val tennhanvienphutrach: String? = "",
    val chitiet: String? = "",
    val tennguon: String? = "",
    val tenmucdo: String? = "",
    val diachitrienkhai: String? = "",
    val tentrangthai: String? = "",

    val tenquytrinh: String? = "",
    val nguoitao: String? = "",
    val ngaybatdau: String? = "",
    val ngayketthuc: String? = "",
    val nhanvienphutrach: String? = "",
    val idtrangthai: String? = "",
    val mautrangthai: String? = "",
    val tongtien: Double? = 0.0,
    val tongtienconno: Double? = 0.0,
    val tongtiendatt: Double? = 0.0,
    val tongiatri: Double? = 0.0,

    val hanmucchiphi: Double? = 0.0,
    val tylethanhcong: Float? = 0f,
    val tamdung: Boolean? = false,
    val xoa: Boolean? = false,
    val chuyensangduan: Boolean? = false,

)
