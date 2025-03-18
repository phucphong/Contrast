package com.contrast.Contrast.presentation.components.alertDialog

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.contrast.Contrast.R
import com.contrast.Contrast.presentation.components.inputs.CustomTextField

// thông báo chọn số tiền
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MoneySelectionDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onAmountSelected: (String) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    var customAmount by remember { mutableStateOf("") }
                    val amounts = listOf(
                        stringResource(id = R.string.amount_10k),
                        stringResource(id = R.string.amount_50k),
                        stringResource(id = R.string.amount_100k),
                        stringResource(id = R.string.amount_200k),
                        stringResource(id = R.string.amount_500k),
                        stringResource(id = R.string.amount_1m)
                    )

                    CustomTextField(
                        value = customAmount,
                        onValueChange = { customAmount = it },
                        placeholder = stringResource(id = R.string.other_amount),  // Use string resource for placeholders
                        keyboardType = KeyboardType.Number,

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    amounts.forEach { amount ->
                        Text(
                            text = amount,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onAmountSelected(amount)
                                    onDismiss()
                                }
                                .padding(vertical = 12.dp)
                        )
                    }
                }
            }
        }
    }
}