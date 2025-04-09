package com.itechpro.domain.enumApp

import com.itechpro.domain.model.Category


enum class CategoryType(
    val endpoint: String,
    val obj: String,
    val mode: String,
    val extractId: (Category) -> String?, // function trích ID phù hợp
    val extractName: (Category) -> String? // function trích tên
) {
    //người phụ trách

    CONTACT_PERSON(
        endpoint = "/ex/api/getobj",
        obj = "nguoiphutrach",
        mode = "getallbyidcongty",
        extractId = { it.idnhanvien ?.replace(".0","")},
        extractName = { it.hoten }
    ),

    // nhân viên
    EMPLOYEE(
        endpoint = "/ex/api/getobj",
        obj = "nhanvien",
        mode = "getallbyidcongty",
        extractId = { it.idnhanvien ?.replace(".0","")},
        extractName = { it.hoten }
    ),

    // nguòi giới thiệu
    INTRODUCER(
        endpoint = "/ex/api/getobj",
        obj = "lienhe",
        mode = "getallbyidcongty",
        extractId = { it.id?.replace(".0","") },
        extractName = { it.hoten }
    ),
    // cơ hội kinh doanh

    OPPORTUNITY(
        endpoint = "/ex/apiaffiliate/getobj",
        obj = "cohoi",
        mode = "getallbyidcongty",
        extractId = { it.idcohoi?.replace(".0","") },
        extractName = { it.hoten }
    ),

    // cấp độ
    LOOKUP_LEVEL(
        endpoint = "/ex/apiaffiliate/getobj",
        obj = "duan",
        mode = "getallbyidcongty",
        extractId = { it.id ?.replace(".0","")},
        extractName = { it.ten }
    ),

    // chính sách
    LOOKUP_POLICY(
        endpoint = "/ex/api/getobj",
        obj = "khachhang",
        mode = "getlistnhomchinhsach",
        extractId = { it.id?.replace(".0","") },
        extractName = { it.ten }
    ),
    // Nhóm ngành nghề
    LOOKUP_INDUSTRY(
        endpoint = "/ex/api/getobj",
        obj = "nguonkhachhang",
        mode = "getallbyidcongty",
        extractId = { it.id?.replace(".0","") },
        extractName = { it.ten }
    ),
    // nguồn thông tin
    LOOKUP_INFO_SOURCE(
        endpoint = "/ex/api/getobj",
        obj = "nguonkhachhang",
        mode = "getallbyidcongty",
        extractId = { it.id?.replace(".0","") },
        extractName = { it.ten }
    ),


    STATUS_CUSTOMER(
        endpoint = "/ex/api/getobj",
        obj = "khachhang",
        mode = "laytrangthai",
        extractId = { it.id?.replace(".0","") },
        extractName = { it.ten }
    ),

    //dự án
    PROJECT(
        endpoint = "/ex/apiaffiliate/getobj",
        obj = "duan",
        mode = "getallbyidcongty",
        extractId = { it.id ?.replace(".0","")},
        extractName = { it.ten }
    ),
    // thạng thái dự án
    STATUS_PROJECT(
        endpoint = "/ex/api/getobj",
        obj = "danhmuctrangthai",
        mode = "getallbyidcongtybyloai",
        extractId = { it.id?.replace(".0","") },
        extractName = { it.hoten }
    ),




}
