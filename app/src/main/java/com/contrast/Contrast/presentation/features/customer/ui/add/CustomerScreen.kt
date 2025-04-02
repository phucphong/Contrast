package com.contrast.Contrast.presentation.features.customer.ui.add

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.bottomSheet.DatePickerBottomSheet
import com.contrast.Contrast.presentation.components.category.CategoryDialog
import com.contrast.Contrast.presentation.components.category.CategoryDialogMultiSelect
import com.contrast.Contrast.presentation.components.dropdown.CustomDropdown
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
import com.contrast.Contrast.presentation.features.customer.CustomerFieldType
import com.contrast.Contrast.presentation.features.register.viewmodel.RegisterAccountViewModel
import com.contrast.Contrast.presentation.theme.FF7C7C7C
import com.itechpro.domain.enumApp.CategoryType
import com.itechpro.domain.model.Category
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScreen(
    navController: NavController,
    ido: String,
    viewModel: RegisterAccountViewModel = hiltViewModel()
) {
    val fieldValues = remember { mutableStateMapOf<CustomerFieldType, String>() }
    val fieldNames = remember { mutableStateMapOf<CustomerFieldType, String>() }
    val fieldIds = remember { mutableStateMapOf<CustomerFieldType, String>() }

    var showDatePicker by remember { mutableStateOf(false) }
    var datePickerTargetField by remember { mutableStateOf<CustomerFieldType?>(null) }
    var showDialogForKey by remember { mutableStateOf<CustomerFieldType?>(null) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    CustomerFieldType.entries.forEach { key ->
        if (fieldValues[key] == null) {
            fieldValues[key] = if (key == CustomerFieldType.CUSTOMER_TYPE) "Cá nhân" else ""
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CustomTopAppBarBackTitleSave(
            title = stringResource(id = R.string.customer_add),
            onBackClick = { navController.popBackStack() },
            onSaveClick = {
                val idContactPerson = fieldIds[CustomerFieldType.CONTACT_PERSON]
                val idIndustry = fieldIds[CustomerFieldType.INDUSTRY]
                Log.d("SAVE", "ContactPerson ID: $idContactPerson, Industry ID: $idIndustry")
            }
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(CustomerFieldType.entries.toList()) { key ->
                CustomText(
                    color = FF7C7C7C,
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
                        textAlign = TextAlign.Start,
                        showUnderline = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    CustomTextField(
                        value = fieldNames[key] ?: "",
                        onValueChange = { fieldNames[key] = it },
                        placeholder = stringResource(id = key.labelResId),
                        readOnly = key.isDialogPicker || key == CustomerFieldType.ESTABLISH_DATE || key == CustomerFieldType.CARE_DATE,
                        isRequired = key.required,
                        onClickReadOnly = when {
                            key.isDialogPicker -> {
                                { showDialogForKey = key }
                            }
                            key == CustomerFieldType.ESTABLISH_DATE || key == CustomerFieldType.CARE_DATE -> {
                                {
                                    datePickerTargetField = key
                                    showDatePicker = true
                                }
                            }
                            else -> null
                        }
                    )
                }
            }
        }

        showDialogForKey?.let { key ->
            key.categoryType?.let { categoryType ->
                if (key.isMultiSelect) {
                    CategoryDialogMultiSelect(
                        title = stringResource(id = key.labelResId),
                        typeCheck = categoryType,
                        preSelectedIds = fieldIds[key]?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
                        preSelectedNames = fieldNames[key]?.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
                        onOptionSelectedIds = { selectedIds ->
                            fieldIds[key] = selectedIds.joinToString(",")
                        },
                        onOptionSelectedNames = { selectedNames ->
                            fieldNames[key] = selectedNames.joinToString(", ")
                        },
                        onDismiss = { showDialogForKey = null }
                    )
                } else {
                    CategoryDialog(
                        title = stringResource(id = key.labelResId),
                        typeCheck = categoryType,
                        searchLocal = true,
                        onOptionSelectedId = { selectedID -> fieldIds[key] = selectedID },
                        onOptionSelectedName = { selectedName -> fieldNames[key] = selectedName },
                        onDismiss = { showDialogForKey = null }
                    )
                }
            }
        }

        if (showDatePicker && datePickerTargetField != null) {
            DatePickerBottomSheet(
                sheetState = sheetState,
                onDismiss = {
                    coroutineScope.launch { sheetState.hide() }
                    showDatePicker = false
                    datePickerTargetField = null
                },
                onDateSelected = { selectedDate ->
                    datePickerTargetField?.let {
                        fieldNames[it] = selectedDate
                    }
                }
            )
        }
    }
}
