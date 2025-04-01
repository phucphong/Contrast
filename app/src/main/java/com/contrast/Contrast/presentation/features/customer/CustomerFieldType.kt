package com.contrast.Contrast.presentation.features.customer

import com.contrast.Contrast.R

enum class CustomerFieldType(
    val labelResId: Int,
    val required: Boolean = false,
    val isDialogPicker: Boolean = false
) {
    CUSTOMER_TYPE(R.string.customer_type, required = true),                       // Cá nhân / Tổ chức (Dropdown)
    CUSTOMER_NAME(R.string.customer_name, required = true),                      // Tên khách hàng
    CONTACT_PERSON(R.string.contact_person, required = true, isDialogPicker = true),  // Người phụ trách (Dialog)
    INTRODUCER(R.string.introducer, isDialogPicker = true),                      // Người giới thiệu (Dialog)
    ESTABLISH_DATE(R.string.establish_date),                                     // Ngày thành lập
    STATUS(R.string.status, isDialogPicker = true),                              // Trạng thái (Dialog)
    INDUSTRY(R.string.industry_group, isDialogPicker = true),                    // Nhóm ngành nghề (Dialog)
    POLICY_GROUP(R.string.policy_group, isDialogPicker = true),                  // Nhóm chính sách (Dialog)
    INFO_SOURCE(R.string.info_source, isDialogPicker = true),                    // Nguồn thông tin (Dialog)
    NOTE(R.string.note),                                                         // Ghi chú
    LEVEL(R.string.level, isDialogPicker = true),                                // Cấp độ (Dialog)
    REPRESENTATIVE(R.string.representative, isDialogPicker = true),              // Người đại diện (Dialog)
    POSITION(R.string.position),                                                 // Chức vụ
    TAX_CODE(R.string.tax_code),                                                 // Mã số thuế
    BANK_ACCOUNT(R.string.bank_account),                                         // Tài khoản ngân hàng
    BANK_NAME(R.string.bank_name),                                               // Tên ngân hàng
    CARE_DATE(R.string.care_date),                                               // Ngày chăm sóc
    CARE_STAFF(R.string.care_staff, isDialogPicker = true),                      // Người chăm sóc (Dialog)
    FACEBOOK(R.string.facebook),                                                 // Facebook
    MAP_LINK(R.string.map)                                                       // Link bản đồ
}