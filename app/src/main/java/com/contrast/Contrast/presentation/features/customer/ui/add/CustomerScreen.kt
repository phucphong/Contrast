package com.contrast.Contrast.presentation.features.customer.ui.add

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.category.CategoryDialog
import com.contrast.Contrast.presentation.components.dropdown.CustomDropdown
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
import com.contrast.Contrast.presentation.features.customer.CustomerFieldType
import com.contrast.Contrast.presentation.features.register.viewmodel.RegisterAccountViewModel
import com.contrast.Contrast.presentation.theme.FF7C7C7C
import com.itechpro.domain.model.Category


@Composable
fun CustomerScreen(
    navController: NavController,
    ido: String,
    viewModel: RegisterAccountViewModel = hiltViewModel()
) {
    val fieldValues = remember { mutableStateMapOf<CustomerFieldType, String>() }
    CustomerFieldType.entries.forEach { key ->
        if (fieldValues[key] == null) {
            fieldValues[key] = if (key == CustomerFieldType.CUSTOMER_TYPE) "Cá nhân" else ""
        }
    }

    val dialogOptions = remember { mutableStateListOf<Category>() }
    var showDialogForKey by remember { mutableStateOf<CustomerFieldType?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CustomTopAppBarBackTitleSave(
            title = stringResource(id = R.string.customer_add),
            onBackClick = { navController.popBackStack() },
            onSaveClick = { /* TODO: Save logic */ }
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(CustomerFieldType.entries.toList()) { key ->
                CustomText(
                    color= FF7C7C7C,
                    text = buildAnnotatedString {
                        append(stringResource(id = key.labelResId))
                        if (key.required) withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                    }
                )

                if (key == CustomerFieldType.CUSTOMER_TYPE) {
                    CustomDropdown(
                        options = listOf("Cá nhân", "Tổ chức"),
                        selectedOption = fieldValues[key] ?: "",
                        onOptionSelected = { fieldValues[key] = it },
                        placeholder = stringResource(id = key.labelResId),
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    CustomTextField(
                        value = fieldValues[key] ?: "",
                        onValueChange = { fieldValues[key] = it },
                        placeholder = stringResource(id = key.labelResId),
                        readOnly = key.isDialogPicker,
                        onClickReadOnly = if (key.isDialogPicker) {
                            {
                                showDialogForKey = key
                                dialogOptions.clear()

                            }
                        } else null
                    )


                }
            }
        }
        LaunchedEffect(showDialogForKey) {
            showDialogForKey?.let { key ->
                dialogOptions.clear()
                val options = getFakeCustomerOptions(key)
                dialogOptions.addAll(options)
            }
        }

        showDialogForKey?.let { key ->
            CategoryDialog(
                title = stringResource(id = key.labelResId),
                options = dialogOptions,
                onOptionSelected = { selected ->
                    Log.e("selected",selected)
                    fieldValues[key] = selected
                },
                onOptionSelectedName = { selected ->
                    Log.e("selected",selected)
                    fieldValues[key] = selected
                },
                onDismiss = { showDialogForKey = null }
            )
        }
    }
}
fun getFakeCustomerOptions(type: CustomerFieldType): List<Category> {
    return when (type) {
        CustomerFieldType.CONTACT_PERSON -> listOf(
            Category(id=1, name="Nguyễn Văn A","",""),
            Category(id=2, name="Trần Thị B","",""),
            Category(id=3, name="Lê Văn C","",""),
        )
        CustomerFieldType.INTRODUCER -> listOf(
            Category(id=4, name="Người giới thiệu 1","",""),
            Category(id=5, name="Người giới thiệu 2","",""),
        )
        CustomerFieldType.STATUS -> listOf(
            Category(id=6, name="Mới","",""),
            Category(id=7, name="Tiềm năng","",""),
            Category(id=8, name="Chăm sóc","",""),
            Category(id=9, name="Không liên hệ","",""),
        )
        CustomerFieldType.LEVEL -> listOf(
            Category(id=10, name="VIP","",""),
            Category(id=11, name="Thân thiết","",""),
            Category(id=12, name="Mới","",""),
            Category(id=13, name="Khách lẻ","",""),
        )
        else -> listOf(
            Category(-1, name="Lựa chọn A","",""),
            Category(-2, name="Lựa chọn B","",""),
        )
    }
}
