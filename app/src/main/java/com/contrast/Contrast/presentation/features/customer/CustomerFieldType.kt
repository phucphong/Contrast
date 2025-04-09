package com.contrast.Contrast.presentation.features.customer

import com.contrast.Contrast.R
import com.itechpro.domain.model.FieldMeta
import com.itechpro.domain.enumApp.CategoryType
import com.itechpro.domain.model.FieldMetaData




enum class CustomerFieldType(
    private val meta: FieldMetaData,
    val categoryType: CategoryType? = null
) : FieldMeta by meta {

    CUSTOMER_TYPE(FieldMetaData(R.string.customer_type, required = true)),


    CUSTOMER_NAME(FieldMetaData(R.string.customer_name, required = true)),
    CONTACT_PERSON(
        FieldMetaData(
            labelResId = R.string.contact_person,
            required = true,
            isDialogPicker = true,
//                    isMultiSelect = true// chọn nhiều  dữ liệu

        ),
        categoryType = CategoryType.CONTACT_PERSON
    ),

    INTRODUCER(
        FieldMetaData(
            labelResId = R.string.introducer,
            isDialogPicker = true
        ),
        categoryType = CategoryType.INTRODUCER
    ),
    PHONE_NUMBER(FieldMetaData(R.string.phoneNumber)),
    ESTABLISH_DATE(FieldMetaData(R.string.establish_date)),
    ADDRESS(FieldMetaData(R.string.address)),
    EMAIL(FieldMetaData(R.string.email)),
    WEBSITE(FieldMetaData(R.string.website)),
    TYPE(FieldMetaData(R.string.customer_type)),

    STATUS_CUSTOMER(
        FieldMetaData(
            labelResId = R.string.status,
            isDialogPicker = true
        ),
        categoryType = CategoryType.STATUS_CUSTOMER
    ),

    INDUSTRY(
        FieldMetaData(
            labelResId = R.string.industry_group,
            isDialogPicker = true
        ),
        categoryType = CategoryType.LOOKUP_INDUSTRY
    ),

    POLICY_GROUP(
        FieldMetaData(
            labelResId = R.string.policy_group,
            isDialogPicker = true
        ),
        categoryType = CategoryType.LOOKUP_POLICY
    ),

    INFO_SOURCE(
        FieldMetaData(
            labelResId = R.string.info_source,
            isDialogPicker = true
        ),
        categoryType = CategoryType.LOOKUP_INFO_SOURCE
    ),

    NOTE(FieldMetaData(R.string.note)),
//    NOTEWORK(FieldMetaData(R.string.note_work)),

    LEVEL(
        FieldMetaData(
            labelResId = R.string.level,
            isDialogPicker = true
        ),
        categoryType = CategoryType.LOOKUP_LEVEL
    ),

    REPRESENTATIVE(
        FieldMetaData(
            labelResId = R.string.representative

        )
    ),
    POSITION(FieldMetaData(R.string.position)),

    TAX_CODE(FieldMetaData(R.string.tax_code)),

    BANK_ACCOUNT(FieldMetaData(R.string.bank_account)),
    BANK_NAME(FieldMetaData(R.string.bank_name)),
    CARE_DATE(FieldMetaData(R.string.care_date)),
    CARE_STAFF(
        FieldMetaData(
            labelResId = R.string.care_staff,
            isDialogPicker = true,

        ),
        categoryType = CategoryType.EMPLOYEE
    ),
    FACEBOOK(FieldMetaData(R.string.facebook)),
    ZALO(FieldMetaData(R.string.facebook)),
    MAP_LINK(FieldMetaData(R.string.map))
}
