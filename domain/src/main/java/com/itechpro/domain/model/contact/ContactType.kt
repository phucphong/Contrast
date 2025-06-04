package com.itechpro.domain.model.contact


enum class ContactType(
    val endpoint: String,
    val obj: String,
    val mode: String,

) {
    //người phụ trách


    CONTACT_CUSTOMER(
        endpoint = "/ex/api/getobj",
        obj = "nguoiphutrach",
        mode = "getallbyidcongty",

        ),
    ADD_CONTACT_OPPORTUNITY(
        endpoint = "/ex/api/addlienhecohoikinhdoanh",
        obj = "",
        mode = "",

        ),
    CONTACT_OPPORTUNITY_NOT_IMPLEMENT(
        endpoint = "/ex/api/getobj",
        obj = "lienhecohoikinhdoanh",
        mode = "getallbyidcongtybyidcohoikinhdoanhchualienket",

        ),
    CONTACT_PRJOECT_NOT_IMPLEMENT(
        endpoint = "/ex/api/addlienheduan",
        obj = "lienheduan",
        mode = "getallbyidcongtybyidduanchualienket",

        ),

  ADD_CONTACT_PRJOECT(
        endpoint = "/ex/api/getobj",
        obj = "",
        mode = "",

        ),

    CONTACT_DETAIL(
        endpoint = "/ex/api/getobj",
        obj = "lienhe",
        mode = "getbyid",

        ),
  CONTACT_CALL(
        endpoint = "/ex/api/getobj",
        obj = "",
        mode = "",

        ),
 CONTACT_SMS(
        endpoint = "/ex/api/getobj",
        obj = "",
        mode = "",

        ),
 CONTACT_EMAIL(
        endpoint = "/ex/api/getobj",
        obj = "",
        mode = "",

        ),





}
