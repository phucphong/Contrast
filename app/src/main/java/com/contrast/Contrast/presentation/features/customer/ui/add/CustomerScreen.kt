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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.alertDialog.CustomAlertDialog
import com.contrast.Contrast.presentation.components.dateTimePicker.CustomDateTimePickerDialog
import com.contrast.Contrast.presentation.components.category.CategoryDialog
import com.contrast.Contrast.presentation.components.category.CategoryDialogMultiSelect
import com.contrast.Contrast.presentation.components.circularProgressIndicatorCentered.CustomCircularProgressIndicatorDialog
import com.contrast.Contrast.presentation.components.datePicker.CustomDatePickerDialog
import com.contrast.Contrast.presentation.components.dropdown.CustomDropdown
import com.contrast.Contrast.presentation.components.inputs.CustomTextField
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.components.topAppBar.CustomTopAppBarBackTitleSave
import com.contrast.Contrast.presentation.features.customer.CustomerFieldType
import com.contrast.Contrast.presentation.features.customer.viewmodel.CustomerViewModel
import com.contrast.Contrast.presentation.features.register.viewmodel.RegisterAccountViewModel
import com.contrast.Contrast.presentation.theme.FF7C7C7C
import com.itechpro.domain.model.NetworkResponse
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScreen(
    navController: NavController, ido: String, viewModel: CustomerViewModel = hiltViewModel()
) {

    val registerState by viewModel.registerState.collectAsState()
    val validationError by viewModel.validationError.collectAsState()

    var isAlertDialogVisible by remember { mutableStateOf(false) }
    var isPersonalCustomer by remember { mutableStateOf(false) }
    var isRegisterButton by remember { mutableStateOf(false) }

    var careStaffId by remember { mutableStateOf("0") }
    var contactPersonId by remember { mutableStateOf("0") }
    var industryGroupId by remember { mutableStateOf("0") }
    var idIntroducer by remember { mutableStateOf("0") }
    var statusId by remember { mutableStateOf("0") }
    var levelId by remember { mutableStateOf("0") }
    var infoSourceId by remember { mutableStateOf("0") }
    var policyGroupId by remember { mutableStateOf("0") }
    var partnerId1 by remember { mutableStateOf("0") }
    var partnerId2 by remember { mutableStateOf("0") }
    var processId by remember { mutableStateOf("0") }
    var countryId by remember { mutableStateOf("0") }
    var provinceId by remember { mutableStateOf("0") }
    var districtId by remember { mutableStateOf("0") }
    var wardId by remember { mutableStateOf("0") }

    var createTask by remember { mutableStateOf(false) }

    val fieldTypeValues = remember { mutableStateMapOf<CustomerFieldType, String>() }
    val fieldNames = remember { mutableStateMapOf<CustomerFieldType, String>() }
    val fieldIds = remember { mutableStateMapOf<CustomerFieldType, String>() }

    var showDatePicker by remember { mutableStateOf(false) }
    var datePickerTargetField by remember { mutableStateOf<CustomerFieldType?>(null) }
    var showDialogForKey by remember { mutableStateOf<CustomerFieldType?>(null) }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    val namePrefixes =
        remember { mutableStateMapOf<CustomerFieldType, String>() } // Lưu xưng hô cho CUSTOMER_NAME

    CustomerFieldType.entries.forEach { key ->
        if (fieldTypeValues[key] == null) {
            fieldTypeValues[key] = if (key == CustomerFieldType.CUSTOMER_TYPE) "Cá nhân" else ""
        }
    }

    val currentUser by viewModel.currentUser.collectAsState()

    LaunchedEffect(currentUser) {
        currentUser?.let {
            if (fieldIds[CustomerFieldType.CONTACT_PERSON].isNullOrEmpty()) {
                fieldIds[CustomerFieldType.CONTACT_PERSON] = it.id
                fieldNames[CustomerFieldType.CONTACT_PERSON] = it.name
            }
        }
    }


    LaunchedEffect(registerState) {
        when (registerState) {
            is NetworkResponse.Success -> {
                isAlertDialogVisible = false
                isRegisterButton = false
            }

            is NetworkResponse.Error -> {
                isAlertDialogVisible = false
                isRegisterButton = false
            }

            is NetworkResponse.Loading -> {
                isAlertDialogVisible = true
            }
        }
    }

    if (validationError != null) {
        CustomAlertDialog(message = validationError!!, onDismiss = {
            viewModel.clearValidationError()
            isAlertDialogVisible = false
            isRegisterButton = false
        })
    }

    if (isAlertDialogVisible && isRegisterButton && validationError == null) {
        CustomCircularProgressIndicatorDialog(networkState = registerState, onDismiss = {
            isAlertDialogVisible = false
            isRegisterButton = false
        })
    }


    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CustomTopAppBarBackTitleSave(title = stringResource(id = R.string.customer_add),
            onBackClick = { navController.popBackStack() },
            onSaveClick = {


                contactPersonId =
                    fieldIds[CustomerFieldType.CONTACT_PERSON]?: ""// người phụ trách
                industryGroupId =
                    fieldIds[CustomerFieldType.INDUSTRY]?: ""// nhóm ngành nghề
                idIntroducer =
                    fieldIds[CustomerFieldType.INTRODUCER]?: ""// người giới thiệu
                statusId = fieldIds[CustomerFieldType.STATUS_CUSTOMER]?: ""// trang thái
                levelId = fieldIds[CustomerFieldType.LEVEL]?: ""//cấp độ khác hàng
                infoSourceId =
                    fieldIds[CustomerFieldType.INFO_SOURCE]?: ""// nguồn khách hàng
                policyGroupId =
                    fieldIds[CustomerFieldType.POLICY_GROUP]?: ""// chính sách khách hàng

                isRegisterButton = true



                viewModel.validateAndRegister(

                    ido,
                    countryId,
                    provinceId,
                    districtId,
                    wardId,
                    statusId,
                    infoSourceId,
                    contactPersonId,
                    industryGroupId,
                    levelId,
                    processId,
                    careStaffId,
                    policyGroupId,
                    industryGroupId,
                    fieldNames[CustomerFieldType.ESTABLISH_DATE]?: "",
                    fieldNames[CustomerFieldType.ESTABLISH_DATE]?: "",// xưng hô
                    fieldNames[CustomerFieldType.CUSTOMER_NAME]?: "",
                    fieldNames[CustomerFieldType.PHONE_NUMBER]?: "",
                    fieldNames[CustomerFieldType.ADDRESS]?: "",
                    fieldNames[CustomerFieldType.EMAIL]?: "",
                    fieldNames[CustomerFieldType.WEBSITE]?: "",
                    fieldNames[CustomerFieldType.BANK_NAME]?: "",
                    fieldNames[CustomerFieldType.BANK_ACCOUNT]?: "",
                    fieldNames[CustomerFieldType.ESTABLISH_DATE]?: "",
                    fieldNames[CustomerFieldType.CARE_DATE]?: "",
                    fieldNames[CustomerFieldType.FACEBOOK]?: "",
                    fieldNames[CustomerFieldType.ZALO]?: "",
                    fieldNames[CustomerFieldType.MAP_LINK]?: "",
                    fieldNames[CustomerFieldType.NOTE]?: "",
                    fieldNames[CustomerFieldType.NOTE]?: "",
                    "",
                    "",
                    "",
                    "",
                    createTask,
                    isPersonalCustomer
                )
            })

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
             isPersonalCustomer = fieldTypeValues[CustomerFieldType.CUSTOMER_TYPE] == "Cá nhân"

            val visibleFields = CustomerFieldType.entries

            items(visibleFields) { key ->
                CustomText(color = FF7C7C7C, text = buildAnnotatedString {
                    append(stringResource(id = key.labelResId))
                    if (key.required) withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
                })

                if (key == CustomerFieldType.CUSTOMER_TYPE) {


                    CustomDropdown(
                        options = listOf("Cá nhân", "Tổ chức"),
                        selectedOption = fieldTypeValues[key] ?: "",
                        onOptionSelected = { fieldTypeValues[key] = it },
                        placeholder = stringResource(id = key.labelResId),
                        textAlign = TextAlign.Start,
                        showUnderline = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    if (key == CustomerFieldType.CUSTOMER_NAME && isPersonalCustomer) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CustomDropdown(
                                options = listOf(
                                    stringResource(R.string.pronoun_grandpa),
                                    stringResource(R.string.pronoun_grandma),
                                    stringResource(R.string.pronoun_mr),
                                    stringResource(R.string.pronoun_ms)
                                ),
                                selectedOption = namePrefixes[key] ?: "",
                                onOptionSelected = { namePrefixes[key] = it },
                                placeholder = stringResource(R.string.pronoun),
                                textAlign = TextAlign.Start,
                                showUnderline = false,
                                modifier = Modifier
                                    .width(130.dp)
                                    .padding(top = 5.dp, end = 8.dp),
                                fullWith = false
                            )

                            CustomTextField(value = fieldNames[key] ?: "",
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
                                })
                        }
                    } else {
                        // Các field bình thường, chỉ có TextField
                        CustomTextField(value = fieldNames[key] ?: "",
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
                            })
                    }

                }
            }
        }

        showDialogForKey?.let { key ->
            key.categoryType?.let { categoryType ->
                if (key.isMultiSelect) {
                    CategoryDialogMultiSelect(title = stringResource(id = key.labelResId),
                        typeCheck = categoryType,
                        preSelectedIds = fieldIds[key]?.split(",")?.filter { it.isNotBlank() }
                            ?: emptyList(),
                        preSelectedNames = fieldNames[key]?.split(",")?.filter { it.isNotBlank() }
                            ?: emptyList(),
                        onOptionSelectedIds = { selectedIds ->
                            fieldIds[key] = selectedIds.joinToString(",")
                        },
                        onOptionSelectedNames = { selectedNames ->
                            fieldNames[key] = selectedNames.joinToString(", ")
                        },
                        onDismiss = { showDialogForKey = null })
                } else {
                    CategoryDialog(title = stringResource(id = key.labelResId),
                        typeCheck = categoryType,
                        searchLocal = true,
                        onOptionSelectedId = { selectedID -> fieldIds[key] = selectedID },
                        onOptionSelectedName = { selectedName -> fieldNames[key] = selectedName },
                        onDismiss = { showDialogForKey = null })
                }
            }
        }

        if (showDatePicker && datePickerTargetField != null) {


            CustomDatePickerDialog(initialDate = fieldNames[CustomerFieldType.ESTABLISH_DATE],


                onDismiss = {
                    coroutineScope.launch { sheetState.hide() }
                    showDatePicker = false
                    datePickerTargetField = null
                }, onDateSelected = { selectedDate ->
                    datePickerTargetField?.let {
                        fieldNames[it] = selectedDate
                    }
                })


//            CustomDateTimePickerDialog(
//                initialDateTime = fieldNames[CustomerFieldType.ESTABLISH_DATE],
//                minDateTime="01/01/2024 00:00",
//
//                onDismiss = {
//                    coroutineScope.launch { sheetState.hide() }
//                    showDatePicker = false
//                    datePickerTargetField = null
//                },
//                onDateTimeSelected = { selectedDate ->
//                    datePickerTargetField?.let {
//                        fieldNames[it] = selectedDate
//                    }
//                }
//            )
        }
    }
}
