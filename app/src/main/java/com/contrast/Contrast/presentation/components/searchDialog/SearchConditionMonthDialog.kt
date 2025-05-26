package com.contrast.Contrast.presentation.components.searchDialog


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.extensions.DateUtils.getCurrentTimeLabel
import com.contrast.Contrast.extensions.DateUtils.getStartAndEndDate
import com.contrast.Contrast.presentation.components.category.CategoryDialog
import com.contrast.Contrast.presentation.components.category.CategoryDialogMultiSelect
import com.contrast.Contrast.presentation.components.datePicker.CustomDatePickerDialog
import com.contrast.Contrast.presentation.components.dropdown.CustomDropdownTimeType
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CompactDropdownField
import com.itechpro.domain.enumApp.CategoryType
import com.itechpro.domain.model.DateFieldType
import com.itechpro.domain.model.SearchDialog
import com.itechpro.domain.model.TimeTypeOption


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchConditionMonthDialog(

    search: SearchDialog,
    type: DateFieldType,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSearch: (SearchDialog) -> Unit,
) {
    var monthYear by remember { mutableStateOf(search.monthYear ?: "") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showAgency by remember { mutableStateOf(false) }
    var showAgencyLevel by remember { mutableStateOf(false) }

    var selectedTypeNew by remember { mutableStateOf(search.selectedType ?: "") }

    var agencys by remember { mutableStateOf(search.agencys ?: "") }
    var agencyIds by remember { mutableStateOf(search.agencyIds ?: "") }
    var agencysLevel by remember { mutableStateOf(search.agencysLevel ?: "") }
    var agencyLevelIds by remember { mutableStateOf(search.agencyLevelIds ?: "") }

    // Hiển thị Date Picker
    if (showDatePicker) {
        CustomDatePickerDialog(initialDate = monthYear, isMonthYearPicker = true, onDismiss = {
            showDatePicker = false

        }, onDateSelected = { selectedDate ->
            monthYear = selectedDate
            showDatePicker = false

        })
    }

    if (showAgency) {
        CategoryDialogMultiSelect(title = stringResource(R.string.agency),
            typeCheck = CategoryType.AGENCY,
            preSelectedIds = agencyIds.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
            preSelectedNames = agencys.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
            onOptionSelectedIds = { selectedIds ->
                agencyIds = selectedIds.joinToString(",")
            },
            onOptionSelectedNames = { selectedNames ->
                agencys = selectedNames.joinToString(", ")
            },
            onDismiss = { showAgency = false })
    }
    if (showAgencyLevel) {
        CategoryDialogMultiSelect(title = stringResource(R.string.level),
            typeCheck = CategoryType.AGENCY_LEVEL,
            preSelectedIds = agencyIds.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
            preSelectedNames = agencys.split(",")?.filter { it.isNotBlank() } ?: emptyList(),
            onOptionSelectedIds = { selectedIds ->
                agencyLevelIds = selectedIds.joinToString(",")
            },
            onOptionSelectedNames = { selectedNames ->
                agencysLevel = selectedNames.joinToString(", ")
            },
            onDismiss = { showAgencyLevel = false })
    }



    Surface(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(2f)
            .background(Color.White),
        shadowElevation = 8.dp,
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {


            DialogHeaderWithCancel(
                title = stringResource(R.string.filter_conditions),
                onDismiss = { onDismiss() })



            CompactDropdownField(value = monthYear, modifier = Modifier.fillMaxWidth(),

                onClick = {
                    showDatePicker = true

                }

            )

            Spacer(modifier = Modifier.height(10.dp))
            if (search.isAgencyLevel) {
                CompactDropdownField(value = agencysLevel,
                    hint = stringResource(R.string.level),
                    modifier = Modifier.fillMaxWidth(),
                    isDownOrClose = true,
                    painter = painterResource(R.drawable.closeview),
                    onClick = {
                        showAgencyLevel = true
                    },
                    onClickDownOrClose = {
                        agencyLevelIds = ""
                        agencysLevel = ""
                    }

                )

                Spacer(modifier = Modifier.height(10.dp))
            }
            if (search.isAgency) {
                CompactDropdownField(value = agencys,
                    hint = stringResource(R.string.agency),
                    modifier = Modifier.fillMaxWidth(),
                    isDownOrClose = true,
                    painter = painterResource(R.drawable.closeview),
                    onClick = {
                        showAgency = true
                    },
                    onClickDownOrClose = {
                        agencyIds = ""
                        agencys = ""
                    })

                Spacer(modifier = Modifier.height(10.dp))
            }
            PrimarySearchButton(onClick = {
                val obj = SearchDialog(
                    monthYear=monthYear,selectedType= selectedTypeNew, agencyIds=agencyIds, agencys=agencys, agencyLevelIds=agencyLevelIds, agencysLevel=agencysLevel
                )
                onSearch(obj)
            })
        }




    }
}

