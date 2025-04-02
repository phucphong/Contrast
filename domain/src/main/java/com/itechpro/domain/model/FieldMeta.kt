package com.itechpro.domain.model

interface FieldMeta {
    val labelResId: Int
    val required: Boolean
    val isDialogPicker: Boolean
    val isMultiSelect: Boolean
}
