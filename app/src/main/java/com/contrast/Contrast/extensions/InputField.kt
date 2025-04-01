package com.contrast.Contrast.extensions

import androidx.compose.ui.text.input.KeyboardType
import com.itechpro.domain.enumApp.DialogFieldKey

data class InputField(
    val labelResId: Int,
    val value: String,
    val onValueChange: (String) -> Unit,
    val placeholderResId: Int? = null,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val required: Boolean = false,
    val readOnly: Boolean = false, // ✅ Bổ sung để dùng trong các trường chỉ cho chọn

    // Dropdown với dữ liệu tĩnh
    val isDropdown: Boolean = false,
    val dropdownOptions: List<String>? = null,

    // Dialog chọn dữ liệu từ API
    val isDialogPicker: Boolean = false,
    val dialogFieldKey: DialogFieldKey? = null,
    val dialogOptions: List<String>? = null,
    val selectedId: String? = null,
    val onOptionSelected: ((id: String, name: String) -> Unit)? = null
)