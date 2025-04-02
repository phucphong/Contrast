package com.itechpro.domain.model

data class FieldMetaData(
    override val labelResId: Int,
    override val required: Boolean = false,
    override val isDialogPicker: Boolean = false,
    override val isMultiSelect: Boolean = false
) : FieldMeta
