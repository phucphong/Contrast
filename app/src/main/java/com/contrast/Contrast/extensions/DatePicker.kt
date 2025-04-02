package com.contrast.Contrast.extensions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.contrast.Contrast.presentation.components.modifier.noRippleClickableComposable
import com.contrast.Contrast.presentation.components.text.CustomText
import com.contrast.Contrast.presentation.theme.FF0967DF
import com.ozcanalasalvar.datepicker.compose.datepicker.WheelDatePicker
import com.ozcanalasalvar.datepicker.utils.DateUtils
@Composable
fun IOSStyleDatePicker(
    onDateSelected: (String) -> Unit,
    onDone: () -> Unit
) {
    var selectedDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Row {
            Box (Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( end = 8.dp, bottom = 8.dp)
                    .noRippleClickableComposable {
                        onDateSelected(selectedDate)
                        onDone()
                    },

                ){
            CustomText(
                text = "OK",
                color = FF0967DF,
                textAlign = TextAlign.End
            )

            }
        }

        WheelDatePicker(
            offset = 3,
            yearsRange = IntRange(1900, 2100),
            startDate = com.ozcanalasalvar.datepicker.model.Date(DateUtils.getCurrentTime()),
            textSize = 19,
            selectorEffectEnabled = true,
            darkModeEnabled = false,
            onDateChanged = { day, month, year, _ ->
                selectedDate = formatDateDDMMYYYY(day, month, year)
            }
        )
    }
}
