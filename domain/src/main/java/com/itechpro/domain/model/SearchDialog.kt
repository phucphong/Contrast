package com.itechpro.domain.model


data class SearchDialog(
    var startDate: String,   // Hiển thị cho người dùng
    var endDate: String ,
    var selectedType: String ,


    // Dùng để xử lý logic, gửi API...
)