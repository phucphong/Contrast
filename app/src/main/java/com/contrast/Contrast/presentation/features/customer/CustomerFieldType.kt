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
        categoryType = CategoryType.EMPLOYEE
    ),

    ESTABLISH_DATE(FieldMetaData(R.string.establish_date)),

    STATUS(
        FieldMetaData(
            labelResId = R.string.status,
            isDialogPicker = true
        ),
        categoryType = CategoryType.OPPORTUNITY
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

    LEVEL(
        FieldMetaData(
            labelResId = R.string.level,
            isDialogPicker = true
        ),
        categoryType = CategoryType.LOOKUP_LEVEL
    ),

    REPRESENTATIVE(
        FieldMetaData(
            labelResId = R.string.representative,
            isDialogPicker = true
        ),
        categoryType = CategoryType.RESPONSIBLE_PERSON
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
            isMultiSelect = true
        ),
        categoryType = CategoryType.EMPLOYEE
    ),

    FACEBOOK(FieldMetaData(R.string.facebook)),
    MAP_LINK(FieldMetaData(R.string.map))
}
fun CustomerFieldType.isReadOnlyField(): Boolean {
    return isDialogPicker || this == CustomerFieldType.ESTABLISH_DATE
}