package com.itechpro.domain.model



// ✅ BaseModel: Lớp cha với `os` mặc định là "Android"

// ✅ Account kế thừa BaseModel
data class Customer(

    val os: String? = "android",

    var ma: String? = null,
    var xungho: String? = null,
    var ten: String? = null,
    var hoten: String? = null,
    var id: String? = null,
    var ido: String? = null,
    var mamenu: String? = null,

    var device: String? = null,
    var hanhdong: String? = null,
    var noidungchinh: String? = null,
    var maduthuong: String? = null,
    var tenchuongtrinh: String? = null,
    var thoigian: String? = null,
    var facebook: String? = null,
    var zalo: String? = null,
    var ggmap: String? = null,
    var ghichu: String? = null,
    var ghichucongviec: String? = null,
    var iddoitac1: String? = null,
    var iddoitac2: String? = null,

    var dienthoai: String? = null,
    var diachi: String? = null,
    var email: String? = null,
    var website: String? = null,
    var ngaysinh: String? = null,
    var ngaythanhlapcongty: String? = null,
    var nguoidaidien: String? = null,
    var tennganhang: String? = null,
    var taikhoannganhang: String? = null,
    var ngaychamsoc: String? = null,
    var datalanh: String? = null,
    var chucvunguoidaidien: String? = null,
    var masothue: String? = null,
    var ngayxacnhan_khmt: String? = null,
    var fax: String? = null,
    var loai: String? = null,
    var isdailygioithieu: String? = null,

    var iddailygioithieu: String? = null,
    var idnguoiphutrach: String? = null,
    var idcapdo: String? = null,
    var idquytrinh: String? = null,
    var idnguonthongtin: String? = null,
    var idnguoigioithieu: String? = null,
    var idnguoichamsoc: String? = null,
    var idtrangthai: String? = null,
    var idnhomchinhsach: String? = null,
    var idnhomnganhnghe: String? = null,
    var idquocgia: String? = null,
    var idtinhthanh: String? = null,
    var idquanhuyen: String? = null,
    var idphuongxa: String? = null,
    var taocongviec: Boolean? = false,


    var key: String? = null

)
