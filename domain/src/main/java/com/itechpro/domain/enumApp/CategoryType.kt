package com.itechpro.domain.enumApp

import com.itechpro.domain.model.Category


enum class CategoryType(
    val endpoint: String,
    val obj: String,
    val mode: String,
    val extractId: (Category) -> String?, // function trích ID phù hợp
    val extractName: (Category) -> String? // function trích tên
) {
    CONTACT_PERSON(
        endpoint = "/ex/api/getobj",
        obj = "nguoiphutrach",
        mode = "getallbyidcongty",
        extractId = { it.idnhanvien },
        extractName = { it.hoten }
    ),
    EMPLOYEE(
        endpoint = "/ex/api/getobj",
        obj = "nhanvien",
        mode = "getallbyidcongty",
        extractId = { it.idnhanvien?.toString() },
        extractName = { it.hoten }
    ),
    OPPORTUNITY(
        endpoint = "/ex/apiaffiliate/getobj",
        obj = "cohoi",
        mode = "getallbyidcongty",
        extractId = { it.idcohoi },
        extractName = { it.hoten }
    ),
    PROJECT(
        endpoint = "/ex/apiaffiliate/getobj",
        obj = "duan",
        mode = "getallbyidcongty",
        extractId = { it.id }, // fallback nếu cần
        extractName = { it.hoten }
    ),
    // cấp độ
    LOOKUP_LEVEL(
        endpoint = "/ex/apiaffiliate/getobj",
        obj = "duan",
        mode = "getallbyidcongty",
        extractId = { it.id }, // fallback nếu cần
        extractName = { it.hoten }
    ),

    // chính sách
    LOOKUP_POLICY(
        endpoint = "/ex/api/getobj",
        obj = "khachhang",
        mode = "getlistnhomchinhsach",
        extractId = { it.id }, // fallback nếu cần
        extractName = { it.hoten }
    ),
    // cNhóm ngành nghề
    LOOKUP_INDUSTRY(
        endpoint = "/ex/api/getobj",
        obj = "nguonkhachhang",
        mode = "getallbyidcongty",
        extractId = { it.id }, // fallback nếu cần
        extractName = { it.hoten }
    ),    // nguồn thông tin
    LOOKUP_INFO_SOURCE(
        endpoint = "/ex/api/getobj",
        obj = "nguonkhachhang",
        mode = "getallbyidcongty",
        extractId = { it.id }, // fallback nếu cần
        extractName = { it.hoten }
    ),


    RESPONSIBLE_PERSON(
        endpoint = "/ex/apiaffiliate/getobj",
        obj = "duan",
        mode = "getallbyidcongty",
        extractId = { it.id }, // fallback nếu cần
        extractName = { it.hoten }
    ),


}
