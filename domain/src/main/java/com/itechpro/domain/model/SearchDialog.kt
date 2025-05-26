package com.itechpro.domain.model

import java.time.Year


data class SearchDialog(
    var startDate: String?="",   // Hiển thị cho người dùng
    var endDate: String?="" ,
    var monthYear: String?="" ,
    var selectedType: String?="" ,
    var agencyIds: String ?="",
    var agencys: String?="",
    var agencyLevelIds: String?="" ,
    var agencysLevel: String?="" ,
    var isAgencyLevel: Boolean =false ,
    var isAgency: Boolean =false ,



    // Dùng để xử lý logic, gửi API...
)