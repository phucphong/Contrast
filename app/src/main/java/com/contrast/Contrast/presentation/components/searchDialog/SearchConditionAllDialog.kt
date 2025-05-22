package com.contrast.Contrast.presentation.components.searchDialog



import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.contrast.Contrast.R
import com.contrast.Contrast.extensions.DateUtils
import com.contrast.Contrast.extensions.DateUtils.getCurrentTimeLabel
import com.contrast.Contrast.extensions.DateUtils.getStartAndEndDate
import com.contrast.Contrast.presentation.components.datePicker.CustomDatePickerDialog
import com.contrast.Contrast.presentation.components.dropdown.CustomDropdownTimeType
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CompactDropdownField
import com.itechpro.domain.model.DateFieldType
import com.itechpro.domain.model.SearchDialog
import com.itechpro.domain.model.TimeTypeOption



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchConditionAllDialog(
    onDismiss: () -> Unit,
    onSearch: (SearchDialog) -> Unit,
    startDate: String,
    endDate: String,
    selectedType: String,
    modifier: Modifier = Modifier,
) {
    var startDateNew by remember { mutableStateOf(startDate) }
    var endDateNew by remember { mutableStateOf(endDate) }
    var showDatePicker by remember { mutableStateOf(false) }
    var pickingField by remember { mutableStateOf<DateFieldType?>(null) }
    var selectedTypeNew by remember { mutableStateOf(selectedType) }


    val timeTypeOptions = listOf(
        TimeTypeOption(stringResource(R.string.day), "DAY"),
        TimeTypeOption(stringResource(R.string.week), "WEEK"),
        TimeTypeOption(stringResource(R.string.month), "MONTH"),
        TimeTypeOption(stringResource(R.string.quarte), "QUARTER"),
        TimeTypeOption(stringResource(R.string.year), "YEAR")
    )


    // Hiển thị Date Picker
    if (showDatePicker && pickingField != null) {
        val initialDate = if (pickingField == DateFieldType.START) startDateNew else endDateNew

        CustomDatePickerDialog(initialDate = initialDate, onDismiss = {
            showDatePicker = false
            pickingField = null
        }, onDateSelected = { selectedDate ->
            if (pickingField == DateFieldType.START) {
                startDateNew = selectedDate
            } else {
                endDateNew = selectedDate
            }
            showDatePicker = false
            pickingField = null
        })
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

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.filter_conditions),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(R.string.cancel),
                    color = Color(0xFF00B8A9),
                    modifier = Modifier
                        .noRippleClickableComposable { onDismiss() }
                        .padding(4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))


//            CompactDropdownField(
//                value = stringResource(R.string.date_label),
//                isRequired = true,
//                isDown = true,
//                modifier = Modifier.fillMaxWidth(),
//                onClick = { /* future dropdown */ }
//            )


            CustomDropdownTimeType(
                options = timeTypeOptions,
                selectedOption = selectedType,
                onOptionSelected = { option ->
                    val (start, end) = getStartAndEndDate(option.value)
                    startDateNew = start
                    endDateNew = end
                    selectedTypeNew = option.label

                       //

                },
                placeholder = "",

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),

                )


            Spacer(modifier = Modifier.height(16.dp))

            // Từ ngày - Đến ngày
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                CompactDropdownField(value = startDateNew,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = {
                        pickingField = DateFieldType.START
                        showDatePicker = true
                    })

                CompactDropdownField(value = endDateNew,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    onClick = {
                        pickingField = DateFieldType.END
                        showDatePicker = true
                    })
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Nút Tìm kiếm
            Button(
                onClick = {
                    val  obj = SearchDialog(startDateNew, endDateNew, selectedTypeNew)
                    onSearch(obj)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00B8A9))
            ) {
                Text(text = stringResource(R.string.search), color = Color.White)
            }
        }
    }
}
